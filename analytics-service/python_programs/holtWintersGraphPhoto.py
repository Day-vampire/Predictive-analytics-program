# import sys
# import pandas as pd
# import numpy as np
# from sklearn.model_selection import TimeSeriesSplit
# from sklearn.metrics import mean_squared_error
# from scipy.optimize import minimize
# import plotly.graph_objs as go
# from plotly.subplots import make_subplots
# import plotly.io as pio
#
# # ------------------ Аргументы командной строки ------------------
# csv_path = sys.argv[1]
# column_index = int(sys.argv[2])
# season_length = int(sys.argv[3])
# n_preds = int(sys.argv[4])
#
# # ------------------ Загрузка данных ------------------
# df = pd.read_csv(csv_path)
# if column_index < 0 or column_index >= df.shape[1]:
#     raise ValueError(f"Invalid column index: {column_index}. Data has {df.shape[1]} columns.")
#
# data = df.iloc[:, column_index].dropna().reset_index(drop=True)
#
# # ------------------ Модель Хольта-Винтерса ------------------
# class HoltWinters:
#     def __init__(self, series, slen, alpha, beta, gamma, n_preds, scaling_factor=2.56):
#         self.series = series
#         self.slen = slen
#         self.alpha = alpha
#         self.beta = beta
#         self.gamma = gamma
#         self.n_preds = n_preds
#         self.scaling_factor = scaling_factor
#
#     def initial_trend(self):
#         return np.mean([(self.series[i + self.slen] - self.series[i]) / self.slen for i in range(self.slen)])
#
#     def initial_seasonal_components(self):
#         seasonals = {}
#         season_averages = [
#             np.mean(self.series[self.slen * j:self.slen * (j + 1)])
#             for j in range(int(len(self.series) / self.slen))
#         ]
#         for i in range(self.slen):
#             sum_over_avg = sum(
#                 self.series[self.slen * j + i] - season_averages[j]
#                 for j in range(int(len(self.series) / self.slen))
#             )
#             seasonals[i] = sum_over_avg / len(season_averages)
#         return seasonals
#
#     def triple_exponential_smoothing(self):
#         self.result = []
#         self.Smooth = []
#         self.Season = []
#         self.Trend = []
#         self.PredictedDeviation = []
#         self.UpperBond = []
#         self.LowerBond = []
#
#         seasonals = self.initial_seasonal_components()
#         smooth = self.series[0]
#         trend = self.initial_trend()
#
#         for i in range(len(self.series) + self.n_preds):
#             if i == 0:
#                 self.result.append(self.series[0])
#                 self.PredictedDeviation.append(0)
#             elif i >= len(self.series):
#                 m = i - len(self.series) + 1
#                 value = (smooth + m * trend) + seasonals[i % self.slen]
#                 self.result.append(value)
#                 self.PredictedDeviation.append(self.PredictedDeviation[-1] * 1.01)
#             else:
#                 val = self.series[i]
#                 last_smooth, smooth = smooth, self.alpha * (val - seasonals[i % self.slen]) + (1 - self.alpha) * (smooth + trend)
#                 trend = self.beta * (smooth - last_smooth) + (1 - self.beta) * trend
#                 seasonals[i % self.slen] = self.gamma * (val - smooth) + (1 - self.gamma) * seasonals[i % self.slen]
#                 value = smooth + trend + seasonals[i % self.slen]
#                 self.result.append(value)
#                 self.PredictedDeviation.append(
#                     self.gamma * np.abs(val - value) + (1 - self.gamma) * self.PredictedDeviation[-1]
#                 )
#
#             self.UpperBond.append(self.result[-1] + self.scaling_factor * self.PredictedDeviation[-1])
#             self.LowerBond.append(self.result[-1] - self.scaling_factor * self.PredictedDeviation[-1])
#             self.Smooth.append(smooth)
#             self.Trend.append(trend)
#             self.Season.append(seasonals[i % self.slen])
#
# # ------------------ Подбор параметров ------------------
# def timeseriesCVscore(x):
#     errors = []
#     values = data.values
#     alpha, beta, gamma = x
#     tscv = TimeSeriesSplit(n_splits=4)
#     for train, test in tscv.split(values):
#         model = HoltWinters(values[train], slen=season_length, alpha=alpha, beta=beta, gamma=gamma, n_preds=len(test))
#         model.triple_exponential_smoothing()
#         predictions = model.result[-len(test):]
#         errors.append(mean_squared_error(values[test], predictions))
#     return np.mean(errors)
#
# opt = minimize(timeseriesCVscore, x0=[0.3, 0.1, 0.1], method="TNC", bounds=((0, 1), (0, 1), (0, 1)))
# alpha_final, beta_final, gamma_final = opt.x
# print(f"Best parameters: alpha={alpha_final:.4f}, beta={beta_final:.4f}, gamma={gamma_final:.4f}")
#
# # ------------------ Финальная модель ------------------
# model = HoltWinters(data.values, slen=season_length, alpha=alpha_final, beta=beta_final, gamma=gamma_final, n_preds=n_preds)
# model.triple_exponential_smoothing()
#
# # ------------------ Визуализация и сохранение ------------------
# def plot_holt_winters_to_png(output_path="holt_winters_result.png"):
#     anomalies = np.array([np.nan] * len(data))
#     anomalies[data.values < model.LowerBond[:len(data)]] = data.values[data.values < model.LowerBond[:len(data)]]
#
#     fig = make_subplots(
#         rows=2, cols=1,
#         subplot_titles=("Временной ряд и модель", "Прогноз с границами")
#     )
#
#     fig.add_trace(go.Scatter(y=data.values, mode='lines', name='Фактические данные'), row=1, col=1)
#     fig.add_trace(go.Scatter(y=model.result[:len(data)], mode='lines', name='Сглаживание'), row=1, col=1)
#
#     full_index = list(range(len(model.result)))
#     fig.add_trace(go.Scatter(x=full_index, y=model.result, mode='lines', name='Модель'), row=2, col=1)
#     fig.add_trace(go.Scatter(x=full_index, y=model.UpperBond, mode='lines', name='Upper Bound', line=dict(dash='dash')), row=2, col=1)
#     fig.add_trace(go.Scatter(x=full_index, y=model.LowerBond, mode='lines', name='Lower Bound', line=dict(dash='dash')), row=2, col=1)
#     fig.add_trace(go.Scatter(x=list(range(len(anomalies))), y=anomalies, mode='markers', name='Аномалии', marker=dict(color='red', size=8)), row=2, col=1)
#
#     fig.update_layout(height=800, width=1000, title_text='Holt-Winters анализ')
#
#     pio.write_image(fig, output_path, format='png')
#     print(f"График сохранён: {output_path}")
#
# plot_holt_winters_to_png()

import sys
import pandas as pd
import numpy as np
from sklearn.model_selection import TimeSeriesSplit
from sklearn.metrics import mean_squared_error
from scipy.optimize import minimize
import plotly.graph_objs as go
from plotly.subplots import make_subplots
import io
import base64

# ------------------ Аргументы командной строки ------------------

csv_path = sys.argv[1]
column_index = int(sys.argv[2])
season_length = int(sys.argv[3])
n_preds = int(sys.argv[4])

# ------------------ Загрузка данных ------------------

df = pd.read_csv(csv_path)

if column_index < 0 or column_index >= df.shape[1]:
    raise ValueError(f"Invalid column index: {column_index}. Data has {df.shape[1]} columns.")

data = df.iloc[:, column_index].dropna().reset_index(drop=True)

# ------------------ Модель Хольта-Винтерса ------------------

class HoltWinters:
    def __init__(self, series, slen, alpha, beta, gamma, n_preds, scaling_factor=2.56):
        self.series = series
        self.slen = slen
        self.alpha = alpha
        self.beta = beta
        self.gamma = gamma
        self.n_preds = n_preds
        self.scaling_factor = scaling_factor

    def initial_trend(self):
        return np.mean([(self.series[i + self.slen] - self.series[i]) / self.slen for i in range(self.slen)])

    def initial_seasonal_components(self):
        seasonals = {}
        season_averages = [np.mean(self.series[self.slen * j:self.slen * (j + 1)])
                           for j in range(int(len(self.series) / self.slen))]
        for i in range(self.slen):
            sum_over_avg = sum(self.series[self.slen * j + i] - season_averages[j]
                               for j in range(len(season_averages)))
            seasonals[i] = sum_over_avg / len(season_averages)
        return seasonals

    def triple_exponential_smoothing(self):
        self.result = []
        self.Smooth = []
        self.Season = []
        self.Trend = []
        self.PredictedDeviation = []
        self.UpperBond = []
        self.LowerBond = []

        seasonals = self.initial_seasonal_components()
        smooth = self.series[0]
        trend = self.initial_trend()

        for i in range(len(self.series) + self.n_preds):
            if i == 0:
                self.result.append(self.series[0])
                self.PredictedDeviation.append(0)
            elif i >= len(self.series):
                m = i - len(self.series) + 1
                value = (smooth + m * trend) + seasonals[i % self.slen]
                self.result.append(value)
                self.PredictedDeviation.append(self.PredictedDeviation[-1] * 1.01)
            else:
                val = self.series[i]
                last_smooth, smooth = smooth, self.alpha * (val - seasonals[i % self.slen]) + (1 - self.alpha) * (smooth + trend)
                trend = self.beta * (smooth - last_smooth) + (1 - self.beta) * trend
                seasonals[i % self.slen] = self.gamma * (val - smooth) + (1 - self.gamma) * seasonals[i % self.slen]
                value = smooth + trend + seasonals[i % self.slen]
                self.result.append(value)
                self.PredictedDeviation.append(self.gamma * np.abs(val - value) + (1 - self.gamma) * self.PredictedDeviation[-1])

            self.UpperBond.append(self.result[-1] + self.scaling_factor * self.PredictedDeviation[-1])
            self.LowerBond.append(self.result[-1] - self.scaling_factor * self.PredictedDeviation[-1])
            self.Smooth.append(smooth)
            self.Trend.append(trend)
            self.Season.append(seasonals[i % self.slen])

# ------------------ Кросс-валидация и подбор параметров ------------------

def timeseriesCVscore(x):
    errors = []
    values = data.values
    alpha, beta, gamma = x
    tscv = TimeSeriesSplit(n_splits=4)
    for train, test in tscv.split(values):
        model = HoltWinters(values[train], slen=season_length, alpha=alpha, beta=beta, gamma=gamma, n_preds=len(test))
        model.triple_exponential_smoothing()
        predictions = model.result[-len(test):]
        errors.append(mean_squared_error(values[test], predictions))
    return np.mean(errors)

opt = minimize(timeseriesCVscore, x0=[0.3, 0.1, 0.1], method="TNC", bounds=((0, 1), (0, 1), (0, 1)))
alpha_final, beta_final, gamma_final = opt.x

# ------------------ Обучение финальной модели ------------------

model = HoltWinters(data.values, slen=season_length, alpha=alpha_final, beta=beta_final, gamma=gamma_final, n_preds=n_preds)
model.triple_exponential_smoothing()

# ------------------ Визуализация и экспорт ------------------

def plot_holt_winters(return_fig=False):
    anomalies = np.array([np.nan] * len(data))
    anomalies[data.values < model.LowerBond[:len(data)]] = data.values[data.values < model.LowerBond[:len(data)]]

    fig = make_subplots(rows=2, cols=1, subplot_titles=("Временной ряд и модель", "Прогноз с границами"))

    fig.add_trace(go.Scatter(y=data.values, mode='lines', name='Фактические данные'), row=1, col=1)
    fig.add_trace(go.Scatter(y=model.result[:len(data)], mode='lines', name='Сглаживание'), row=1, col=1)

    full_index = list(range(len(model.result)))
    fig.add_trace(go.Scatter(x=full_index, y=model.result, mode='lines', name='Модель'), row=2, col=1)
    fig.add_trace(go.Scatter(x=full_index, y=model.UpperBond, mode='lines', name='Upper Bound', line=dict(dash='dash')), row=2, col=1)
    fig.add_trace(go.Scatter(x=full_index, y=model.LowerBond, mode='lines', name='Lower Bound', line=dict(dash='dash')), row=2, col=1)
    fig.add_trace(go.Scatter(x=list(range(len(anomalies))), y=anomalies, mode='markers', name='Аномалии', marker=dict(color='red', size=8)), row=2, col=1)

    fig.update_layout(height=800, width=1000, title_text='Holt-Winters анализ')

    if return_fig:
        return fig
    else:
        fig.show()

# Генерация графика и возврат параметров + base64
fig = plot_holt_winters(return_fig=True)

buf = io.BytesIO()
fig.write_image(buf, format="png")
buf.seek(0)
img_base64 = base64.b64encode(buf.read()).decode('utf-8')

params_string = f"Best parameters: alpha={alpha_final:.4f}, beta={beta_final:.4f}, gamma={gamma_final:.4f}"
print(params_string)
print(img_base64)

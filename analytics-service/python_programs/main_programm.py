from datetime import time

import requests
import json
import sys
import warnings

from plotly.subplots import make_subplots
from sklearn.metrics import mean_squared_error, mean_absolute_error

warnings.filterwarnings('ignore')
from tqdm import tqdm

import pandas as pd
import numpy as np

import statsmodels.formula.api as smf
import statsmodels.tsa.api as smt
import statsmodels.api as sm
import scipy.stats as scs
from scipy.optimize import minimize

import matplotlib.pyplot as plt

from plotly.offline import download_plotlyjs, init_notebook_mode, plot  #: Импортирует функции для работы с Plotly в оффлайн-режиме.
from plotly import graph_objs as go                                     #: Импортирует объектные модели графиков Plotly, которые используются для построения графиков.
from sklearn.model_selection import TimeSeriesSplit
from itertools import product

dataset = pd.read_csv('G:/PyCharmPojects/ModuleProg/sorted_data.csv')
dataset.set_index('timestamp', inplace=True)


########################### Отображение графиков в формате html#############################
def plotly_df(df, title=''):
    data = []

    for column in ['open', 'high', 'low', 'close']:
        trace = go.Scatter(
            x=df.index,
            y=df[column],
            mode='lines',
            name=column
        )
        data.append(trace)

    layout = dict(
        title=title,
        xaxis=dict(
            title='Date',
            tickformat='%Y-%m-%d',  # Формат даты
            showgrid=True,
            gridcolor='lightgray',
            ticks='outside',  # Выносим метки за пределы графика
            tickangle=-45,  # Угол наклона меток
        ),
        yaxis=dict(
            title='Price',
            showgrid=True,
            gridcolor='lightgray'
        )
    )

    fig = dict(data=data, layout=layout)
    plot(fig, show_link=False)

# plotly_df(dataset, title="Bitcoin in all position")

############################################################################################


########################### Отображение сглаженного тренда#############################

def plotMovingAverage(series, n):
    """
    series - dataframe with timeseries
    n - rolling window size
    """

    rolling_mean = series.rolling(window=n).mean()
    rolling_std = series.rolling(window=n).std()
    upper_bond = rolling_mean + 1.96 * rolling_std
    lower_bond = rolling_mean - 1.96 * rolling_std

    # Создание графиков
    data = []

    # График скользящего среднего
    trace_mean = go.Scatter(
        x=series.index,
        y=rolling_mean,
        mode='lines',
        name='График скользящего среднего',
        line=dict(color='green')
    )
    data.append(trace_mean)

    # График верхней границы
    trace_upper = go.Scatter(
        x=series.index,
        y=upper_bond,
        mode='lines',
        name='График верхней границы',
        line=dict(color='red', dash='dash')
    )
    data.append(trace_upper)

    # График нижней границы
    trace_lower = go.Scatter(
        x=series.index,
        y=lower_bond,
        mode='lines',
        name='График нижней границы',
        line=dict(color='red', dash='dash')
    )
    data.append(trace_lower)

    # График фактических значений
    trace_actual = go.Scatter(
        x=series.index,
        y=series,
        mode='lines',
        name='График фактических значений',
        line=dict(color='blue')
    )
    data.append(trace_actual)

    # Настройка макета графика
    layout = go.Layout(
        title=f'Сглаживание по дням\n (скользящая средняя) = {n}',
        xaxis=dict(title='Date'),
        yaxis=dict(title=series.name),
        showlegend=True,
        hovermode='closest'
    )

    fig = go.Figure(data=data, layout=layout)
    plot(fig, show_link=False)


# plotMovingAverage(dataset['close'], n=30)

############################################################################################




###################### Код для кросс-валидации на временном ряду + построения модели ###########################


class HoltWinters:

    """
    Модель Хольта-Винтерса с методом Брутлага для детектирования аномалий
    https://fedcsis.org/proceedings/2012/pliks/118.pdf

    # series - исходный временной ряд
    # slen - длина сезона
    # alpha, beta, gamma - коэффициенты модели Хольта-Винтерса
    # n_preds - горизонт предсказаний
    # scaling_factor - задаёт ширину доверительного интервала по Брутлагу (обычно принимает значения от 2 до 3)

    """

    def __init__(self, series, slen, alpha, beta, gamma, n_preds, scaling_factor=1.96):
        self.series = series
        self.slen = slen
        self.alpha = alpha
        self.beta = beta
        self.gamma = gamma
        self.n_preds = n_preds
        self.scaling_factor = scaling_factor

    def initial_trend(self):
        sum = 0.0
        for i in range(self.slen):
            sum += float(self.series[i+self.slen] - self.series[i]) / self.slen
        return sum / self.slen

    def initial_seasonal_components(self):
        seasonals = {}
        season_averages = []
        n_seasons = int(len(self.series)/self.slen)
        # вычисляем сезонные средние
        for j in range(n_seasons):
            season_averages.append(sum(self.series[self.slen*j:self.slen*j+self.slen])/float(self.slen))
        # вычисляем начальные значения
        for i in range(self.slen):
            sum_of_vals_over_avg = 0.0
            for j in range(n_seasons):
                sum_of_vals_over_avg += self.series[self.slen*j+i]-season_averages[j]
            seasonals[i] = sum_of_vals_over_avg/n_seasons
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

        for i in range(len(self.series)+self.n_preds):
            if i == 0: # инициализируем значения компонент
                smooth = self.series[0]
                trend = self.initial_trend()
                self.result.append(self.series[0])
                self.Smooth.append(smooth)
                self.Trend.append(trend)
                self.Season.append(seasonals[i%self.slen])

                self.PredictedDeviation.append(0)

                self.UpperBond.append(self.result[0] +
                                      self.scaling_factor *
                                      self.PredictedDeviation[0])

                self.LowerBond.append(self.result[0] -
                                      self.scaling_factor *
                                      self.PredictedDeviation[0])

                continue
            if i >= len(self.series): # прогнозируем
                m = i - len(self.series) + 1
                self.result.append((smooth + m*trend) + seasonals[i%self.slen])

                # во время прогноза с каждым шагом увеличиваем неопределенность
                self.PredictedDeviation.append(self.PredictedDeviation[-1]*1.01)

            else:
                val = self.series[i]
                last_smooth, smooth = smooth, self.alpha*(val-seasonals[i%self.slen]) + (1-self.alpha)*(smooth+trend)
                trend = self.beta * (smooth-last_smooth) + (1-self.beta)*trend
                seasonals[i%self.slen] = self.gamma*(val-smooth) + (1-self.gamma)*seasonals[i%self.slen]
                self.result.append(smooth+trend+seasonals[i%self.slen])

                # Отклонение рассчитывается в соответствии с алгоритмом Брутлага
                self.PredictedDeviation.append(self.gamma * np.abs(self.series[i] - self.result[i])
                                               + (1-self.gamma)*self.PredictedDeviation[-1])

            self.UpperBond.append(self.result[-1] +
                                  self.scaling_factor *
                                  self.PredictedDeviation[-1])

            self.LowerBond.append(self.result[-1] -
                                  self.scaling_factor *
                                  self.PredictedDeviation[-1])

            self.Smooth.append(smooth)
            self.Trend.append(trend)
            self.Season.append(seasonals[i % self.slen])



def timeseriesCVscore(x):
    # вектор ошибок
    errors = []

    values = data.values
    alpha, beta, gamma = x

    # задаём число фолдов для кросс-валидации
    tscv = TimeSeriesSplit(n_splits=4)

    # идем по фолдам, на каждом обучаем модель, строим прогноз на отложенной выборке и считаем ошибку
    for train, test in tscv.split(values):

        model = HoltWinters(series=values[train], slen = 30, alpha=alpha, beta=beta, gamma=gamma, n_preds=len(test))
        model.triple_exponential_smoothing()

        predictions = model.result[-len(test):]
        actual = values[test]
        error = mean_squared_error(predictions, actual)
        errors.append(error)

    # Возвращаем средний квадрат ошибки по вектору ошибок
    return np.mean(np.array(errors))


data = dataset.close[:-20]                                                               # отложим часть данных для тестирования
x = [0, 0, 0]                                                                            # инициализируем значения параметров
opt = minimize(timeseriesCVscore, x0=x, method="TNC", bounds = ((0, 1), (0, 1), (0, 1))) # Минимизируем функцию потерь с ограничениями на параметры
alpha_final, beta_final, gamma_final = opt.x                                             # Из оптимизатора берем оптимальное значение параметров
print(alpha_final, beta_final, gamma_final)



model = HoltWinters(data, slen = 30, alpha = alpha_final, beta = beta_final, gamma = gamma_final, n_preds = 50, scaling_factor = 2.56)
model.triple_exponential_smoothing()



def plotHoltWinters(data, model):
    # Убедимся, что индексы совпадают
    if len(data) != len(model.result):
        print("Warning: Lengths of data and model do not match.")
        min_length = min(len(data), len(model.result))
        data = data.iloc[:min_length]
        model.result = model.result[:min_length]
        model.UpperBond = model.UpperBond[:min_length]
        model.LowerBond = model.LowerBond[:min_length]

    Anomalies = np.array([np.nan] * len(data))
    Anomalies[data.values < model.LowerBond] = data.values[data.values < model.LowerBond]

    # Создание графиков
    data_traces = []

    # График модели
    trace_model = go.Scatter(
        x=data.index,
        y=model.result,
        mode='lines',
        name='Model',
        line=dict(color='red')
    )
    data_traces.append(trace_model)

    # График верхней и нижней границ
    trace_upper = go.Scatter(
        x=data.index,
        y=model.UpperBond,
        mode='lines',
        name='Upper Confidence',
        line=dict(color='red', dash='dash'),
        opacity=0.5
    )
    data_traces.append(trace_upper)

    trace_lower = go.Scatter(
        x=data.index,
        y=model.LowerBond,
        mode='lines',
        name='Lower Confidence',
        line=dict(color='red', dash='dash'),
        opacity=0.5
    )
    data_traces.append(trace_lower)

    # Заполнение области между верхней и нижней границей
    data_traces.append(go.Scatter(
        x=np.concatenate([data.index, data.index[::-1]]),
        y=np.concatenate([model.UpperBond, model.LowerBond[::-1]]),
        fill='toself',
        fillcolor='rgba(128, 128, 128, 0.5)',
        line=dict(color='rgba(255,255,255,0)'),
        name='Confidence Interval',
        showlegend=False
    ))

    # График фактических значений
    trace_actual = go.Scatter(
        x=data.index,
        y=data.values,
        mode='lines',
        name='Actual',
        line=dict(color='blue', width=2)
    )
    data_traces.append(trace_actual)

    # График аномалий
    trace_anomalies = go.Scatter(
        x=data.index,
        y=Anomalies,
        mode='markers',
        name='Anomalies',
        marker=dict(size=10, color='orange')
    )
    data_traces.append(trace_anomalies)

    # Настройка макета графика
    layout = go.Layout(
        title='Holt-Winters Model',
        xaxis=dict(title='Date'),
        yaxis=dict(title='Values'),
        showlegend=True,
        hovermode='closest'
    )

    fig = go.Figure(data=data_traces, layout=layout)
    plot(fig, show_link=False)

# plotHoltWinters(data,model)


######################################################################################################



######№№№№№№№№№№№№################## Код для ACF и PACF ##############################################


def tsplot(y, lags=None, figsize=(12, 7), style='bmh'):
    if not isinstance(y, pd.Series):
        y = pd.Series(y)

    # Создание подграфиков
    fig = make_subplots(
        rows=2, cols=2,
        subplot_titles=("Данные времянного ряда", "ACF график", "PACF график"),
        specs=[[{"colspan": 2}, None],
               [{"colspan": 1}, {"colspan": 1}]]
    )

    # График временного ряда
    trace_ts = go.Scatter(
        x=y.index,
        y=y,
        mode='lines',
        name='Данные времянного ряда'
    )
    fig.add_trace(trace_ts, row=1, col=1)

    # График ACF
    smt.graphics.plot_acf(y, lags=lags, alpha=0.5)
    acf_values = sm.tsa.stattools.acf(y, nlags=lags)
    acf_index = np.arange(len(acf_values))

    trace_acf = go.Bar(
        x=acf_index,
        y=acf_values,
        name='ACF',
        marker_color='blue'
    )
    fig.add_trace(trace_acf, row=2, col=1)

    # График PACF
    smt.graphics.plot_pacf(y, lags=lags, alpha=0.5)
    pacf_values = sm.tsa.stattools.pacf(y, nlags=lags)
    pacf_index = np.arange(len(pacf_values))

    trace_pacf = go.Bar(
        x=pacf_index,
        y=pacf_values,
        name='PACF',
        marker_color='green'
    )
    fig.add_trace(trace_pacf, row=2, col=2)

    # Настройка макета графика
    fig.update_layout(
        title='Аналитические графики времянныз рядов',
        height=700,
        width=900,
        showlegend=True
    )

    # Критерий Дики-Фуллера
    adf_p_value = sm.tsa.stattools.adfuller(y)[1]
    print(f"Критерий Дики-Фуллера: p={adf_p_value:.5f}")

    fig.show()

# tsplot(dataset.close, lags=30)
######################################################################################################

######№№№№№№№№№№№№################## Код для БОКСА-КОКСА ##############################################

# !
# !"""ИСпользуется только если исходный ряд стационарным не является и
# !критерий Дики-Фуллера не отверг нулевую гипотезу о наличии единичного корня.
# !Метод пробует стабилизировать дисперсию преоразованием Бокса-Кокса.
# !"""
# !
def invboxcox(y,lmbda):
    # обрабтное преобразование Бокса-Кокса
    if lmbda == 0:
        return(np.exp(y))
    else:
        return(np.exp(np.log(lmbda*y+1)/lmbda))

data2 = dataset.copy()
data2['close_box'], lmbd = scs.boxcox(data2.close+1) # прибавляем единицу, так как в исходном ряде есть нули
data2['close_box_season'] = data2.close_box - data2.close_box.shift(30)
data2['close_box_season_diff'] = data2.close_box_season - data2.close_box_season.shift(1)
# tsplot(data2.close_box_season_diff[24*7+1:], lags=30)
# print("Оптимальный параметр преобразования Бокса-Кокса: %f" % lmbd)

######################################################################################################



#################################### ! Подборка хуйни для меня будущего с утра
#################################### ! Разбирайся с этим сам


ps = range(0, 5)
d=1
qs = range(0, 4)
Ps = range(0, 5)
D=1
Qs = range(0, 1)
parameters = product(ps, qs, Ps, Qs)
parameters_list = list(parameters)
len(parameters_list)

results = []
best_aic = float("inf")

for param in tqdm(parameters_list):

    # !try except нужен, потому что на некоторых наборах параметров модель не обучается
    try:
        model=sm.tsa.statespace.SARIMAX(data2.close_box, order=(param[0], d, param[1]),seasonal_order=(param[2], D, param[3], 30)).fit(disp=-1)

    #! выводим параметры, на которых модель не обучается и переходим к следующему набору
    except ValueError:
        print('wrong parameters:', param)
        continue
    aic = model.aic

    #сохраняем лучшую модель, aic, параметры
    if aic < best_aic:
        best_model = model
        best_aic = aic
        best_param = param
    results.append([param, model.aic])

warnings.filterwarnings('default')
result_table = pd.DataFrame(results)
result_table.columns = ['parameters', 'aic']
print(result_table.sort_values(by = 'aic', ascending=True).head())


best_model = sm.tsa.statespace.SARIMAX(data2.close_box,order=(4, d, 3), seasonal_order=(4, D, 1, 30)).fit(disp=-1)
print(best_model.summary())
tsplot(best_model.resid[30:], lags=30)

# data2["arima_model"] = invboxcox(best_model.fittedvalues, lmbd)
# forecast = invboxcox(best_model.predict(start = data2.shape[0], end = data2.shape[0]+10), lmbd)
# forecast = data2.arima_model._append(forecast).values[-30:]
# actual = dataset.close.values[-25:]
#
# # Создание графика
# fig = make_subplots(rows=1, cols=1)
#
# # Предсказанные значения модели
# trace_forecast = go.Scatter(
#     x=np.arange(len(forecast)),
#     y=forecast,
#     mode='lines',
#     name='Model',
#     line=dict(color='red')
# )
# fig.add_trace(trace_forecast)
#
# # Фактические значения
# trace_actual = go.Scatter(
#     x=np.arange(len(actual)),
#     y=actual,
#     mode='lines',
#     name='Actual',
#     line=dict(color='blue')
# )
# fig.add_trace(trace_actual)
#
# # Настройка области прогноза
# fig.add_trace(go.Scatter(
#     x=np.arange(len(actual), len(forecast)),
#     y=forecast[len(actual):],
#     fill='tozeroy',
#     fillcolor='rgba(211, 211, 211, 0.5)',
#     line=dict(color='rgba(255,255,255,0)'),
#     name='Forecast Area',
#     showlegend=False
# ))
#
# # Настройка макета графика
# mae = round(mean_absolute_error(data2.dropna().close, data2.dropna().arima_model))
# fig.update_layout(
#     title=f"SARIMA Model\nMean Absolute Error: {mae} users",
#     xaxis_title='Time',
#     yaxis_title='Closing Price',
#     showlegend=True
# )
#
# # Показать график
# fig.show()

# url = 'https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&symbol=BTC&market=EUR&apikey=demo'
# r = requests.get(url)
# data = r.json()
# Печатаем данные в читаемом формате
# print(json.dumps(data, indent=4, ensure_ascii=False))



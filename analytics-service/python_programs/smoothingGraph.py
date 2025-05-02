import pandas as pd
import plotly.graph_objects as go
from plotly.offline import plot
import sys

# file_name = fileName  # Получаем значение из Java
# second_argument = secondArgument  # Получаем второе значение
file_name = sys.argv[1]  # Имя файла
second_argument = sys.argv[2]  # Второй аргумент
print("File: {}, Second Argument: {}".format(file_name, second_argument))
# Читаем данные
dataset = pd.read_csv(file_name)
dataset.set_index('timestamp', inplace=True)

# Функция для построения графиков
def plotMovingAverage(series, n):
    rolling_mean = series.rolling(window=n).mean()
    rolling_std = series.rolling(window=n).std()
    upper_bond = rolling_mean + 1.96 * rolling_std
    lower_bond = rolling_mean - 1.96 * rolling_std

    data = []

    data.append(go.Scatter(x=series.index, y=rolling_mean, mode='lines', name='График скользящего среднего', line=dict(color='green')))
    data.append(go.Scatter(x=series.index, y=upper_bond, mode='lines', name='График верхней границы', line=dict(color='red', dash='dash')))
    data.append(go.Scatter(x=series.index, y=lower_bond, mode='lines', name='График нижней границы', line=dict(color='red', dash='dash')))
    data.append(go.Scatter(x=series.index, y=series, mode='lines', name='График фактических значений', line=dict(color='blue')))

    layout = go.Layout(title='days smooth', xaxis=dict(title='Date'), yaxis=dict(title=series.name), showlegend=True)
    fig = go.Figure(data=data, layout=layout)

    plot(fig, filename='smoothing_graph.html', auto_open=True)

# Пример использования
plotMovingAverage(dataset['close'], n=30)

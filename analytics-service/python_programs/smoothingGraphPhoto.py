import sys
import pandas as pd
import plotly.graph_objects as go
import numpy as np
import base64
import io

# ------------ Аргументы командной строки ------------
csv_path = sys.argv[1]
column_index = int(sys.argv[2])  # Индекс столбца
n = int(sys.argv[3])             # Окно сглаживания

# ------------ Загрузка данных ------------
df = pd.read_csv(csv_path)
if 'timestamp' not in df.columns:
    raise ValueError("CSV должен содержать столбец 'timestamp'")

df['timestamp'] = pd.to_datetime(df['timestamp'])
df.set_index('timestamp', inplace=True)

if column_index < 0 or column_index >= df.shape[1]:
    raise ValueError(f"Неверный индекс колонки: {column_index}")

series = df.iloc[:, column_index].dropna()

# ------------ Построение скользящего среднего ------------
rolling_mean = series.rolling(window=n).mean()
rolling_std = series.rolling(window=n).std()
upper_bound = rolling_mean + 1.96 * rolling_std
lower_bound = rolling_mean - 1.96 * rolling_std

data = [
    go.Scatter(x=series.index, y=series, mode='lines', name='Фактические значения', line=dict(color='blue')),
    go.Scatter(x=series.index, y=rolling_mean, mode='lines', name='Скользящее среднее', line=dict(color='green')),
    go.Scatter(x=series.index, y=upper_bound, mode='lines', name='Верхняя граница', line=dict(color='red', dash='dash')),
    go.Scatter(x=series.index, y=lower_bound, mode='lines', name='Нижняя граница', line=dict(color='red', dash='dash')),
]

fig = go.Figure(data=data)
fig.update_layout(
    title=f'Скользящее среднее (окно = {n})',
    xaxis_title='Дата',
    yaxis_title=series.name,
    height=600,
    width=1000
)

# ------------ Сохранение в base64 PNG ------------
buffer = io.BytesIO()
fig.write_image(buffer, format='png')
buffer.seek(0)

print("BASE64_IMAGE_START")
print(base64.b64encode(buffer.read()).decode('utf-8'))

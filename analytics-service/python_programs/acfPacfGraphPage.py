import pandas as pd
import plotly.graph_objects as go
from plotly.subplots import make_subplots
import statsmodels.api as sm
import statsmodels.tsa.stattools as smt
import numpy as np
import sys

def tsplot(y, lags=None):
    if not isinstance(y, pd.Series):
        y = pd.Series(y)

    fig = make_subplots(
        rows=3, cols=2,
        specs=[[{"colspan": 2}, None],
               [{}, {}],
               [{"colspan": 2}, None]],
        subplot_titles=("Данные временного ряда", "ACF график", "PACF график", "")
    )

    fig.add_trace(go.Scatter(x=y.index, y=y, mode='lines', name='Временной ряд'), row=1, col=1)

    # ACF
    acf_values, confint_acf = smt.acf(y, nlags=lags, fft=False, alpha=0.05)
    acf_index = np.arange(len(acf_values))

    fig.add_trace(go.Bar(x=acf_index, y=acf_values, name='ACF', marker_color='blue'), row=2, col=1)
    fig.add_trace(go.Scatter(x=acf_index, y=confint_acf[:, 0], mode='lines',
                             line=dict(color='red', dash='dash'), name='ACF 95% CI'), row=2, col=1)
    fig.add_trace(go.Scatter(x=acf_index, y=confint_acf[:, 1], mode='lines',
                             line=dict(color='red', dash='dash'), showlegend=False), row=2, col=1)

    # PACF
    pacf_values, confint_pacf = smt.pacf(y, nlags=lags, method='ywm', alpha=0.05)
    pacf_index = np.arange(len(pacf_values))

    fig.add_trace(go.Bar(x=pacf_index, y=pacf_values, name='PACF', marker_color='green'), row=2, col=2)
    fig.add_trace(go.Scatter(x=pacf_index, y=confint_pacf[:, 0], mode='lines',
                             line=dict(color='red', dash='dash'), name='PACF 95% CI'), row=2, col=2)
    fig.add_trace(go.Scatter(x=pacf_index, y=confint_pacf[:, 1], mode='lines',
                             line=dict(color='red', dash='dash'), showlegend=False), row=2, col=2)

    # === Тесты ===
    test_output = "Результаты статистических тестов:\n"

    # ADF
    adf_result = sm.tsa.adfuller(y)
    test_output += f"\nADF тест (Дики-Фуллер):\n"
    test_output += f"  ADF Statistic: {adf_result[0]:.4f}\n"
    test_output += f"  p-value: {adf_result[1]:.5f}\n"
    for key, value in adf_result[4].items():
        test_output += f"  Критическое значение ({key}%): {value:.4f}\n"

    # KPSS
    try:
        from statsmodels.tsa.stattools import kpss
        kpss_stat, kpss_p, _, kpss_crit = kpss(y, regression='c', nlags="auto")
        test_output += f"\nKPSS тест:\n"
        test_output += f"  KPSS Statistic: {kpss_stat:.4f}\n"
        test_output += f"  p-value: {kpss_p:.5f}\n"
        for key, value in kpss_crit.items():
            test_output += f"  Критическое значение ({key}%): {value}\n"
    except Exception as e:
        test_output += f"\nОшибка KPSS: {e}\n"

    # Ljung-Box
    try:
        lb_test = sm.stats.acorr_ljungbox(y, lags=[lags], return_df=True)
        lb_stat = lb_test['lb_stat'].values[0]
        lb_pval = lb_test['lb_pvalue'].values[0]
        test_output += f"\nLjung-Box тест:\n"
        test_output += f"  LB Statistic (lag {lags}): {lb_stat:.4f}\n"
        test_output += f"  p-value: {lb_pval:.5f}\n"
    except Exception as e:
        test_output += f"\nОшибка Ljung-Box: {e}\n"

    # Добавим текст на график как аннотацию
    fig.add_annotation(
        text=test_output.replace('\n', '<br>'),
        xref="paper", yref="paper",
        x=0.5, y=0.0,
        xanchor='center',
        yanchor='bottom',
        showarrow=False,
        align="left",
        font=dict(size=12),
        bordercolor="black",
        borderwidth=1,
        bgcolor="white"
    )

    # Макет
    fig.update_layout(
        title='Анализ временного ряда: графики и статистика',
        height=1050,
        width=1100,
        showlegend=True
    )

    fig.show()

# Главный блок
if len(sys.argv) < 4:
    print("Usage: python acf_pacf_plot.py <data_file_path> <lags> <column_index>")
    sys.exit(1)

data_file_path = sys.argv[1]
lags = int(sys.argv[2])
column_index = int(sys.argv[3])

try:
    df = pd.read_csv(data_file_path)
    y = df.iloc[:, column_index]
    tsplot(y, lags=lags)
except FileNotFoundError:
    print(f"Ошибка: файл не найден: {data_file_path}")
except IndexError:
    print(f"Ошибка: индекс столбца вне диапазона: {column_index}")
except Exception as e:
    print(f"Произошла ошибка: {e}")

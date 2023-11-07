import numpy as np
import pandas as pd
import yfinance
import datetime
from mplfinance.original_flavor import candlestick_ohlc
import matplotlib.dates as mpl_dates
import matplotlib.pyplot as plt
import sys
plt.rcParams['figure.figsize'] = [12, 7]
plt.rc('font', size=14)


def get_df(stock_code): # 일봉 차트를 정리하여 데이터프레임으로 넣어줌
    name = stock_code
    ticker = yfinance.Ticker(name)
    
    today=datetime.datetime.utcnow()
    day_delta=datetime.timedelta(days=60)
    was=(today-day_delta).strftime('%Y-%m-%d')
    today=today.strftime('%Y-%m-%d')
    
    df = ticker.history(interval="1d",start=was, end=today)
    df['Date'] = pd.to_datetime(df.index)
    df['Date'] = df['Date'].apply(mpl_dates.date2num)
    df = df.loc[:,['Date', 'Open', 'High', 'Low', 'Close']]

    return df,stock_code

def isSupport(df,i):
  support = df['Low'][i] < df['Low'][i-1]  and df['Low'][i] < df['Low'][i+1] and df['Low'][i+1] < df['Low'][i+2] and df['Low'][i-1] < df['Low'][i-2]
  return support
def isResistance(df,i):
  resistance = df['High'][i] > df['High'][i-1]  and df['High'][i] > df['High'][i+1] and df['High'][i+1] > df['High'][i+2] and df['High'][i-1] > df['High'][i-2]
  return resistance


def plot_all(df,stock_name,stock_code): # 위 지지 저항 구한 값으로 주식 차트를 그려줌, 리턴으로 주식 코드를 보내줌
    levels = []
    for i in range(2,df.shape[0]-2):
      if isSupport(df,i):
        levels.append((i,df['Low'][i]))
      elif isResistance(df,i):
        levels.append((i,df['High'][i]))
    
    fig, ax = plt.subplots()
    candlestick_ohlc(ax,df.values,width=0.6, \
                     colorup='green', colordown='red', alpha=0.8)
    date_format = mpl_dates.DateFormatter('%d %b %Y')
    ax.xaxis.set_major_formatter(date_format)
    fig.autofmt_xdate()
    fig.tight_layout()
    for level in levels:
      plt.hlines(level[1],xmin=df['Date'][level[0]],\
                 xmax=max(df['Date']),colors='blue')
    fig.show()
    #fig.savefig(stock_name+'.png')
    fig.savefig('./imgs/'+stock_code+'.png')
    return stock_code


def get_stock(stock_name): # 한글 종목명을 넣으면 주식코드 6자리를 만들어줌 + 코스피의 경우 .KS / 코스닥의 경우 .KQ
    pd_stock=pd.read_excel("./stock_all_list.xlsx", engine="openpyxl", header=0)

    # try:
    #stock_code=str(pd_stock[pd_stock['종목명']==stock_name]['종목코드'].values)[2:][:-2]
    #market_code_tmp=str(pd_stock[pd_stock['종목명']==stock_name]['시장구분'].values)[2:][:-2]
    
    stock_code=str(pd_stock[pd_stock['한글 종목약명']==stock_name]['단축코드'].values)[2:][:-2]
    market_code_tmp=str(pd_stock[pd_stock['한글 종목약명']==stock_name]['시장구분'].values)[2:][:-2]
    

    print(stock_code)
    print(market_code_tmp)
           
    # except:
        # print("예외 발생, 종목명을 정확히 확인하세요!")
        
    if market_code_tmp == 'KOSPI':
          stock_code=stock_code+'.KS'
          print(stock_code)
    elif market_code_tmp == 'KOSDAQ':
          stock_code=stock_code+'.KQ'
          print(stock_code)
        
    return stock_code,stock_name 
    # stock_code.split([','])
    # print(stock_code)
    # if pd_stock[pd_stock['종목명']=='삼성전자']:
    #    print(pd_stock['종목코드'])
    # print(pd_stock)

class Main():
    # while True:
    # get_token()
    # df = get_stock_bong_pd(get_daybong(get_stock("삼성전자")))
    # print(df)

    inputValue=sys.argv[1]
    stock_code,stock_name=get_stock(inputValue)
    #stock_code=sys.argv[1]
    #stock_name=sys.argv[2]
    df,stock_code=get_df(stock_code) 
    plot_all(df,stock_name,stock_code)

if __name__=="__main__":
    Main()
    print(0)



import os.path
from dateutil.utils import today
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup
import pandas as pd
import re
from selenium.webdriver.chrome.service import Service as ChromeService
from selenium.webdriver.chrome.options import Options
from webdriver_manager.chrome import ChromeDriverManager
import sqlite3
from datetime import datetime, timedelta

def initialize_webdriver():
    options = Options()
    # options.add_argument("--headless")

    service = ChromeService(ChromeDriverManager().install())
    driver = webdriver.Chrome(service=service, options=options)
    driver.get("https://www.mse.mk/mk/stats/symbolhistory/REPL")
    return driver

def get_today_date():
    return datetime.now().strftime("%d.%m.%Y")

def previous_day(input_date):
    date_object = datetime.strptime(input_date, "%d.%m.%Y")
    previous_date = date_object - timedelta(days=1)
    return previous_date.strftime("%d.%m.%Y")


def subtract_one_year(date_str):
    date = datetime.strptime(date_str, "%d.%m.%Y")
    new_date = date.replace(year=date.year - 1)
    return new_date.strftime("%d.%m.%Y")

def get_latest_date(conn):
    cursor = conn.cursor()
    date = cursor.execute('SELECT MAX("Датум") FROM stock_data')
    return date.fetchone()[0]



def get_headers():
    return [
        "Датум",
        "Цена на последна трансакција",
        "Мак.",
        "Мин.",
        "Просечна цена",
        "%пром.",
        "Количина",
        "Промет во БЕСТ во денари",
        "Вкупен промет во денари",
        "Stock Symbol"
    ]

def get_day_plus(date_str):
    date_object = datetime.strptime(date_str, "%Y-%m-%d")
    new_date = date_object + timedelta(days=1)
    return new_date.strftime("%d.%m.%Y")

def convert_date_format(date_str):
    date_parts = date_str.split('.')

    if len(date_parts) == 3:

        day = date_parts[0].zfill(2)
        month = date_parts[1].zfill(2)
        year = date_parts[2]

        return f"{year}-{month}-{day}"
    else:
        return date_str


def collect_stock_data_single_period(driver, stock, headers, latestDay, conn):
    option_input = WebDriverWait(driver, 5).until(
        EC.presence_of_element_located((By.CLASS_NAME, "form-control.dropdown"))
    )
    option_input.send_keys(stock)
    button = driver.find_element(By.CLASS_NAME, 'btn-primary-sm')
    button.click()

    WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.ID, "FromDate")))

    all_data = []
    to_date = previous_day(get_today_date())
    from_date = latestDay

    WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.ID, "FromDate")))

    from_date_input = driver.find_element(By.ID, "FromDate")
    to_date_input = driver.find_element(By.ID, "ToDate")

    from_date_input.clear()
    from_date_input.send_keys(from_date)
    to_date_input.clear()
    to_date_input.send_keys(to_date)

    button = driver.find_element(By.CLASS_NAME, 'btn-primary-sm')
    button.click()

    WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.TAG_NAME, "div")))

    try:
        WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.TAG_NAME, "tr")))
    except:
        return


    soup = BeautifulSoup(driver.page_source, 'html.parser')
    tr_elements = soup.find_all('tr')

    for tr in tr_elements:
        td_elements = tr.find_all('td')
        row_data = [td.text.strip().replace('\n', " ") for td in td_elements]
        if row_data:
            row_data.append(stock)
            row_data[0] = convert_date_format(row_data[0])
            all_data.append(row_data)

    if all_data:
        df = pd.DataFrame(all_data, columns=headers)
        df.to_sql("stock_data", con=conn, if_exists="append", index=False)

def get_stocks(driver):
    option_input = WebDriverWait(driver,5).until(
        EC.presence_of_element_located((By.CLASS_NAME, "form-control.dropdown"))
    )
    options = option_input.find_elements(By.TAG_NAME, "option")
    stocks = [option.get_attribute("value") for option in options]
    return [stock for stock in stocks if not re.search(r'\d', stock)]

def collect_stock_data(driver, stock, headers, today, conn):
    option_input = WebDriverWait(driver,5).until(
        EC.presence_of_element_located((By.CLASS_NAME, "form-control.dropdown"))
    )
    option_input.send_keys(stock)
    button = driver.find_element(By.CLASS_NAME, 'btn-primary-sm')
    button.click()

    WebDriverWait(driver,5).until(EC.presence_of_element_located((By.ID, "FromDate")))

    all_data = []
    to_date = previous_day(today)
    from_date = subtract_one_year(today)

    for _ in range(10):
        WebDriverWait(driver,5).until(EC.presence_of_element_located((By.ID, "FromDate")))

        from_date_input = driver.find_element(By.ID, "FromDate")
        to_date_input = driver.find_element(By.ID, "ToDate")

        from_date_input.clear()
        from_date_input.send_keys(from_date)
        to_date_input.clear()
        to_date_input.send_keys(to_date)

        button = driver.find_element(By.CLASS_NAME, 'btn-primary-sm')
        button.click()

        WebDriverWait(driver,5).until(EC.presence_of_element_located((By.TAG_NAME, "div")))

        try:
            WebDriverWait(driver,5).until(EC.presence_of_element_located((By.TAG_NAME, "tr")))
        except:
            break

        soup = BeautifulSoup(driver.page_source, 'html.parser')
        tr_elements = soup.find_all('tr')

        for tr in tr_elements:
            td_elements = tr.find_all('td')
            row_data = [td.text.strip().replace('\n', " ") for td in td_elements]
            if row_data:
                row_data.append(stock)
                row_data[0] = convert_date_format(row_data[0])
                all_data.append(row_data)

        to_date = subtract_one_year(to_date)
        from_date = subtract_one_year(from_date)

    if all_data:
        df = pd.DataFrame(all_data, columns=headers)
        df.to_sql("stock_data", con=conn, if_exists="append", index=False)

def main():
    today = get_today_date()
    headers = get_headers()

    if not os.path.exists('stock_data.db'):
        driver = initialize_webdriver()
        stocks = get_stocks(driver)
        conn = sqlite3.connect('stock_data.db')
        cursor = conn.cursor()

        cursor.execute("DROP TABLE IF EXISTS stock_data")

        cursor.execute('''
            CREATE TABLE IF NOT EXISTS stock_data (
                "Датум" DATE,
                "Цена на последна трансакција" TEXT,
                "Мак." TEXT,
                "Мин." TEXT,
                "Просечна цена" TEXT,
                "%пром." TEXT,
                "Количина" TEXT,
                "Промет во БЕСТ во денари" TEXT,
                "Вкупен промет во денари" TEXT,
                "Stock Symbol" TEXT
            )
        ''')

        for stock in stocks:
            collect_stock_data(driver, stock, headers, today, conn)

        driver.close()
        conn.commit()
        conn.close()
    else:
        print("ok")
        conn = sqlite3.connect('stock_data.db')
        cursor = conn.cursor()
        latest_date = get_latest_date(conn)
        latest_date = get_day_plus(latest_date)
        print(latest_date)
        print(get_today_date())
        if latest_date == get_today_date():
            print("No new data!")
            conn.close()
            return
        else:
            print("Collecting new data")
            driver = initialize_webdriver()
            stocks = get_stocks(driver)
            for stock in stocks:
                collect_stock_data_single_period(driver, stock, headers, latest_date, conn)

            conn.commit()
            conn.close()


main()



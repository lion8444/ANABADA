import pymysql
db = pymysql.connect(host='localhost', port=3306, user='anabada', passwd='anabada', db='anabada', charset='utf8')
cursor = db.cursor()
import requests
from bs4 import BeautifulSoup
def save_data(item_info):
    # 5-1단계 : items 테이블 date insert
    sql = """INSERT INTO category (category_id, category_main, category_sub) VALUES (func_seq_10('CATE'),'""" + item_info['category_name'] + """','""" + item_info['sub_category_name'] + """')""" 
    print(sql)
    cursor.execute(sql)
    db.commit()

def get_items(html, category_name, sub_category_name):
    data_dict = dict()
    data_dict['category_name'] = category_name
    data_dict['sub_category_name'] = sub_category_name
    save_data(data_dict)
# 2단계 : 중분류 클로링
def get_category(category_link, category_name):
    res = requests.get(category_link)
    soup = BeautifulSoup(res.content, 'html.parser')
    sub_categories = soup.select('div.navi.group ul li a')
    for sub_category in sub_categories:
        res = requests.get('http://corners.gmarket.co.kr/'+sub_category['href'])
        soup = BeautifulSoup(res.content, 'html.parser')
        get_items(soup, category_name, sub_category.get_text())        
# 1단계 : 대분류 링크 주소 및 대분류명 크롤링
res = requests.get('http://corners.gmarket.co.kr/Bestsellers')
soup = BeautifulSoup(res.content, 'html.parser')
categories = soup.select('div.gbest-cate ul.by-group li a')
for category in categories:
    get_category('http://corners.gmarket.co.kr/' + category['href'], category.get_text())

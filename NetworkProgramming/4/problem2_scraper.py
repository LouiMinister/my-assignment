import requests
import csv
from bs4 import BeautifulSoup


f = open('./problem2_csv.csv', 'w', encoding='utf-8', newline="")
wr = csv.writer(f)
wr.writerow(["name", "role", "start_year", "end_year",
             "research_interest", "current_role_job", "profile_pic_url"])
webInfo = requests.get("https://sites.google.com/view/davidchoi/home/members")
html = BeautifulSoup(webInfo.text, "html.parser")
htmlSrc = html.find_all(
    'div', 'hJDwNd-AhqUyc-II5mzb purZT-AhqUyc-II5mzb pSzOP-AhqUyc-II5mzb JNdkSc yYI8W')

i = 0
for con in htmlSrc:
    list = con.find('div', "JNdkSc-SmKAyb").text
    if(len(list) == 0):
        list = "NA"
        continue
    name_ary = list.split('(', 1)
    role_split = name_ary[1].split(',', 1)
    start_year_ary = role_split[1].split(')', 1)
    real_end_year = start_year_ary[0][5:]
    real_start_year = start_year_ary[0][1:5]
    if(len(real_end_year) == 0):
        real_end_year = real_start_year
    elif(real_end_year == '-'):
        real_end_year = "NA"
    else:
        real_end_year = real_end_year[1:]

    if "Research Interests" in start_year_ary[1]:
        Research_split = start_year_ary[1].split(':')
        Research = Research_split[1]
        if(len(Research.strip()) == 0):
            Research = "NA"
    else:
        Research = "NA"

    if '@' in start_year_ary[1]:
        current_split = start_year_ary[1].split('@')
        current_role_job = current_split[0].strip()
    else:
        current_role_job = "NA"

    name = name_ary[0]
    role = role_split[0]
    start_year = real_start_year.strip()
    end_year = real_end_year.strip()

    htmlSrc = html.find_all('img', 'CENy8b')
    wr.writerow([name, role, start_year, end_year, Research,
                 current_role_job, htmlSrc[i].get('src')])
    i += 1

import pandas as pd
import numpy as np
from matplotlib import pyplot as plt
import seaborn as sns
import pickle
import plotly.express as px
from plotly.offline import init_notebook_mode, iplot
import plotly.graph_objects as go
import warnings


warnings.filterwarnings("ignore")

# %matplotlib inline

# from google.colab import drive
# drive.mount('/content/gdrive')
data = pd.read_csv('US_Accidents_Dec21_updated.csv')
data.head()
data.columns
len(data.columns)
data.isna().sum()
data['Start_Time'] = pd.to_datetime(data['Start_Time'])
data['End_Time'] = pd.to_datetime(data['End_Time'])
data.groupby(by='State').size().sort_values().plot.pie(autopct='%1.1f%%', shadow=True, figsize=(16, 16))
top_10_state = data[['City', 'State', 'Severity']].groupby('State').agg({'City': 'count',
                                                                         'Severity': 'mean'}).sort_values(
    by='City', ascending=False).head(10)
df_state_city = data[['State', 'City', 'Severity']].groupby(['State', 'City']).count().rename(
    columns={'Severity': 'Count'})

top_10_city = df_state_city.sort_values(by='Count', ascending=False).head(10)

fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(14, 4))

bar = sns.barplot(x=top_10_state.index, y=top_10_state['City'],
                  palette='nipy_spectral_r',
                  #
                  edgecolor='black',
                  ax=ax1)
sns.despine(left=True)
ax1.set_xlabel("State")
ax1.set_ylabel("No. of Accidents", fontdict={'fontsize': 16, 'color': 'MidnightBlue'})
ax1.set_title('Top 10 Accident States in US', fontdict={'fontsize': 16, 'color': 'MidnightBlue'})

bar = sns.barplot(x=top_10_city.index.get_level_values(1), y=top_10_city['Count'],
                  palette='nipy_spectral',
                  edgecolor='black',
                  ax=ax2
                  )
sns.despine(left=True)
ax2.set_xlabel("City")
ax2.set_ylabel("No. of Accidents")
ax2.set_title('Top 10 Accident Cities in US', fontdict={'fontsize': 16, 'color': 'MidnightBlue'})
plt.xticks(rotation=45)

# Working to get labels
total_accidents = len(data)

for p in ax1.patches:
    height = p.get_height()
    ax1.text(p.get_x() + p.get_width() / 2,
             height + 20000,
             '{:.2f}%'.format(height / total_accidents * 100),
             ha="center",
             fontsize=8, color='indianred')

# for City
for p in ax2.patches:
    height = p.get_height()
    ax2.text(p.get_x() + p.get_width() / 2,
             height + 3000,
             '{:.2f}%'.format(height / total_accidents * 100),
             ha="center",
             fontsize=8, color='indianred')

fig.show()

data['Year'] = data['Start_Time'].dt.year
data['Month'] = data['Start_Time'].dt.month
data['Hour'] = data['Start_Time'].dt.hour
diff = data['End_Time'] - data['Start_Time']
data['DelayTime'] = round(diff.dt.seconds / 3600, 1)
year = data['Year'].value_counts()
month = data['Month'].value_counts().sort_index()
month_map = {1: 'Jan', 2: 'Feb', 3: 'Mar', 4: 'Apr', 5: 'May', 6: 'Jun', 7: 'Jul', 8: 'Aug'
    , 9: 'Sep', 10: 'Oct', 11: 'Nov', 12: 'Dec'}

hour_severity = data[['Hour', 'Severity']].groupby('Hour').agg({'Hour': 'count', 'Severity': 'mean'})

data['Day'] = data['Start_Time'].dt.dayofweek
day_severity = data[['Day', 'Severity']].groupby('Day').agg({'Day': 'count', 'Severity': 'mean'})
day_map = {0: 'Monday', 1: 'Tueday', 2: 'Wedday', 3: "Thuday", 4: 'Friday', 5: "Saturday", 6: 'Sunday'}

hour_severity = data[['Hour', 'Severity']].groupby('Hour').agg({'Hour': 'count', 'Severity': 'mean'})

data['Day'] = data['Start_Time'].dt.dayofweek
day_severity = data[['Day', 'Severity']].groupby('Day').agg({'Day': 'count', 'Severity': 'mean'})
day_map = {0: 'Monday', 1: 'Tueday', 2: 'Wedday', 3: "Thuday", 4: 'Friday', 5: "Saturday", 6: 'Sunday'}

fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(14, 5))

# plot for year

light_palette = sns.color_palette(palette='pastel')

year_color_map = ['Lavender' for _ in range(5)]
year_color_map[0] = 'LightCoral'  # light_palette[0]
year_color_map[4] = 'PaleGreen'  # light_palette[2]

years = ax1.bar(year.index.values, year, color=year_color_map, edgecolor='black')
ax1.spines[('top')].set_visible(False)
ax1.spines[('right')].set_visible(False)
ax1.set_xlabel("Years", fontdict={'fontsize': 12, 'color': 'MidnightBlue'})
ax1.set_ylabel("No. of Accidents")
ax1.set_title('Accidents per Years', fontdict={'fontsize': 16, 'color': 'MidnightBlue'})

for p in ax1.patches:
    height = p.get_height()
    ax1.text(p.get_x() + p.get_width() / 2,
             height + 20000,
             '{:.2f}%'.format(height / total_accidents * 100),
             ha="center",
             fontsize=8, color='Blue')

    month_color_map = ['Lavender' for _ in range(12)]
month_color_map[11] = 'LightCoral'  # light_palette[0]
month_color_map[6] = 'PaleGreen'  # light_palette[2]

m = sns.barplot(x=month.index.map(month_map), y=month, ax=ax2, palette=month_color_map, edgecolor='black')
plt.xticks(rotation=60)
ax2.set_xlabel("Months", fontdict={'fontsize': 12, 'color': 'MidnightBlue'})
ax2.set_ylabel("No. of Accidents")
ax2.set_title('Accidents per Months', fontdict={'fontsize': 16, 'color': 'MidnightBlue'})
sns.despine(left=True)

for p in ax2.patches:
    height = p.get_height()
    ax2.text(p.get_x() + p.get_width() / 2,
             height + 8000,
             '{:.2f}%'.format(height / total_accidents * 100),
             ha="center",
             fontsize=8, color='blue')

ax1.grid(axis='y', linestyle='-', alpha=0.4)
ax2.grid(axis='y', linestyle='-', alpha=0.4)

plt.show()

fig = plt.figure(figsize=(16, 4))
data.groupby(by=['Year', 'Month']).size().plot()

fig, (ax, ax2, ax3) = plt.subplots(1, 3, figsize=(16, 6))

sns.set_context('paper')

ax.plot(day_severity['Severity'], color='Turquoise', label=day_map, linewidth=3,
        linestyle='solid', marker='.', markersize=18, markerfacecolor='w', markeredgecolor='b', markeredgewidth='2')

ax.set_xlabel("Days of the week", fontdict={'fontsize': 12, 'color': 'MidnightBlue'})
ax.set_ylabel("Severity Level")
ax.set_title('Severity by day of week', fontdict={'fontsize': 16, 'color': 'MidnightBlue'})

ax2.plot(day_severity['Day'], color='Turquoise', label=day_map, linewidth=3,
         linestyle='solid', marker='.', markersize=18, markerfacecolor='w', markeredgecolor='b', markeredgewidth='2')

ax2.set_xlabel("Days of the week", fontdict={'fontsize': 12, 'color': 'MidnightBlue'})
ax2.set_ylabel("No. of Accidents")
ax2.set_title('Accidents Count by day', fontdict={'fontsize': 16, 'color': 'MidnightBlue'})

f2 = sns.barplot(x=day_severity['Day'].index.map(day_map), y=day_severity['Day'], ax=ax3, palette='nipy_spectral_r')
plt.xticks(rotation=60)
ax3.set_xlabel("Days of the week", fontdict={'fontsize': 12, 'color': 'MidnightBlue'})
ax3.set_title('Accidents count on days of week', fontdict={'fontsize': 16, 'color': 'MidnightBlue'})

sns.despine(left=True)

fig.show()

fig, ax = plt.subplots(1, 1, figsize=(14, 6))

sns.set_context('paper')

f = sns.barplot(x=hour_severity['Hour'].index, y=hour_severity['Hour'], ax=ax, palette='Pastel2')

ax2 = ax.twinx()

ax2.plot(hour_severity['Severity'], color='CornFlowerBlue', label='Severity', linewidth=3,
         linestyle='solid', marker='.', markersize=18, markerfacecolor='w', markeredgecolor='b', markeredgewidth='2')

sns.despine(left=True)
ax2.spines[('top')].set_visible(False)
ax2.spines[('right')].set_visible(False)
ax2.spines[('left')].set_visible(False)
ax.set_xlabel("Hours of the Day", fontdict={'fontsize': 12, 'color': 'MidnightBlue'})
ax.set_ylabel("No. of Accidents")
ax2.set_ylabel("Severity of Accidents", rotation=270, labelpad=20)
ax.set_title('Accidents and Severity per Hour of the day', fontdict={'fontsize': 16, 'color': 'MidnightBlue'})
ax2.legend(loc=(0, 0.8))

ax.annotate('Morning office rush', xytext=(3, 500), xy=(7, 5000), arrowprops={'arrowstyle': 'fancy', 'color': 'Red'})
ax.annotate('Office Returning rush', xytext=(19, 500), xy=(16, 5000),
            arrowprops={'arrowstyle': 'fancy', 'color': 'Red'})

fig.show()

sev_4_mean = data[data['Severity'] == 4][['Severity', 'Year']].groupby('Year').count().mean()

f, (ax1, ax2) = plt.subplots(1, 2, figsize=(16, 6))

data['Severity'].value_counts().plot.pie(autopct='%1.1f%%', ax=ax1, colors=sns.color_palette(palette='Pastel1'),
                                         pctdistance=0.8, explode=[.03, .03, .03, .03],
                                         textprops={'fontsize': 12, 'color': 'DarkSlateBlue'},
                                         labels=['Severity 2', 'Severity 3', 'Severity 4', 'Severity 1']
                                         )

ax1.set_title("Accidents Severity", fontdict={'fontsize': 16, 'color': 'MidnightBlue'})

s = sns.countplot(data=data[['Severity', 'Year']], x='Year', hue='Severity', ax=ax2, palette='rainbow'
                  , edgecolor='black')
ax2.axhline(sev_4_mean[0], color='Blue', linewidth=1, linestyle='dashdot')
ax2.annotate(f"Severity 4 mean : {sev_4_mean[0]}", va='center', ha='center', color='#4a4a4a',
             bbox=dict(boxstyle='round', pad=0.4, facecolor='Wheat', linewidth=0), xy=(1, 80000))

ax2.set_title("Severity levels by years", fontdict={'fontsize': 16, 'color': 'MidnightBlue'})

sns.despine(left=True)

pair = sns.pairplot(data[['Severity', 'Temperature(F)', 'Humidity(%)', 'Pressure(in)']].dropna(), hue='Severity',
                    palette='nipy_spectral')
# pair = sns.pairplot(df[['Severity','Temperature(F)']].dropna(), hue='Severity', palette='nipy_spectral')

pair.fig.suptitle('Distribution of Temp , Humidity and Pressure with Severity', y=1.08
                  , fontsize=16, color='MidnightBlue', ha='center', va='top')

plt.show()

data['Severity'] = data['Severity'].astype('int')

features = ['Severity', 'Temperature(F)', 'Humidity(%)',
            'Pressure(in)', 'Visibility(mi)', 'Wind_Direction', 'Wind_Speed(mph)',
            'Precipitation(in)', 'Amenity', 'Bump', 'Crossing',
            'Give_Way', 'Junction', 'No_Exit', 'Railway', 'Roundabout', 'Station',
            'Stop', 'Traffic_Calming', 'Traffic_Signal',
            'Sunrise_Sunset']

mask = np.zeros_like(data[features].corr(), dtype=np.bool)
mask[np.triu_indices_from(mask)] = True

plt.figure(figsize=(16, 12))
sns.heatmap(data[features].corr(), cmap=sns.diverging_palette(240, 10, as_cmap=True), square=True,
            annot=True, fmt='.2f', center=0, linewidth=2, cbar=True, mask=mask)

plt.show()

BBox = ((data.Start_Lng.min(), data.Start_Lng.max(), data.Start_Lat.min(), data.Start_Lat.max()))
# map_pic = plt.imread('map1.png')


fig, ax = plt.subplots(figsize=(26, 14))
ax.scatter(data[data['Severity'] == 1].Start_Lng + 0.3, data[data['Severity'] == 1].Start_Lat - 0.8, zorder=1,
           alpha=0.7, c='blue', s=4)
ax.scatter(data[data['Severity'] == 2].Start_Lng + 0.3, data[data['Severity'] == 2].Start_Lat - 0.8, zorder=1,
           alpha=0.7, c='green', s=3)
ax.scatter(data[data['Severity'] == 3].Start_Lng + 0.3, data[data['Severity'] == 3].Start_Lat - 0.8, zorder=1,
           alpha=0.7, c='orange', s=2)
ax.scatter(data[data['Severity'] == 4].Start_Lng + 0.3, data[data['Severity'] == 4].Start_Lat - 0.8, zorder=1,
           alpha=0.7, c='red', s=1)

ax.set_xlim(BBox[0], BBox[1])
ax.set_ylim(BBox[2], BBox[3])
# ax.imshow(map_pic, zorder=0, extent = BBox, aspect= 'auto', interpolation='none')
# ax.imshow(map_pic, zorder=2, alpha= 0.5, extent = BBox, aspect= 'auto')

from wordcloud import WordCloud, STOPWORDS

text = ' '.join(data['Description'].to_list())
wordcloud = WordCloud(width=400, height=400, background_color='white', stopwords=STOPWORDS).generate(str(text))
fig = plt.figure(figsize=(5, 5))
plt.imshow(wordcloud)
plt.axis('off')
plt.tight_layout(pad=0)
plt.show()

fig = plt.figure(figsize=(16, 6))
sns.countplot(x="Weather_Condition", data=data, order=data['Weather_Condition'].value_counts()[:12].index,
              hue='Severity', palette='Blues_d')
plt.legend(loc='lower right')
plt.show()

from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import train_test_split

# import tensorflow as tf
data.info()
data.isna().mean()
null_columns = ['End_Lat', 'End_Lng', 'Number', 'Wind_Chill(F)', 'Precipitation(in)']

data.drop(null_columns, axis=1, inplace=True)
data.isna().sum()

data.dropna(axis=0, inplace=True)
data.head()
print("Total missing values:", data.isna().sum().sum())

{column: len(data[column].unique()) for column in data.columns if data.dtypes[column] == 'object'}

unneeded_columns = ['ID', 'Description', 'Street', 'City', 'Zipcode', 'Country']
data.columns.to_list()
data.drop(unneeded_columns, axis=1, inplace=True)


def get_years(df, column):
    return df[column].apply(lambda date: date[0:4])


def get_months(df, column):
    return df[column].apply(lambda date: date[5:7])


data.columns.to_list()

import datetime

date = data['Start_Time']
print(len(date))
months_list = []
for i in range(len(date)):
    #     h=data.iloc[i]
    #     h=h['Start_Time']
    #     str_h= str(h)
    #     #print(h)
    #     h= datetime.datetime.strptime(str_h, "%Y-%m-%d %H:%M:%S")
    #     months_=h.month
    # print(months_)
    # print(h)
    months = data.iloc[i]
    months = months['Start_Time'].month
    months_list.append(months)
data['Start_Time_Month'] = months_list
print(data['Start_Time_Month'])
# print(months)

date = data['Start_Time']
print(len(date))
years_list = []
for i in range(len(data)):
    years = data.iloc[i]
    years = years['Start_Time'].year
    years_list.append(years)
data['Start_Time_Year'] = years_list
print(data['Start_Time_Year'])

date = data['End_Time']
print(len(date))
months_list = []
for i in range(len(data)):
    months = data.iloc[i]
    months = months['End_Time'].month
    months_list.append(months)
data['End_Time_Month'] = months_list

date = data['End_Time']
print(len(date))
years_list = []
for i in range(len(data)):
    years = data.iloc[i]
    years = years['End_Time'].year
    years_list.append(years)
data['End_Time_Year'] = years_list

print(data['Weather_Timestamp'])
print(data['Start_Time'])

data = data.drop(['Start_Time', 'End_Time', 'Weather_Timestamp'], axis=1)

print("Total missing values:", data.isna().sum().sum())

from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import train_test_split

# import tensorflow as tf

from sklearn import preprocessing

le = preprocessing.LabelEncoder()

columns = ['Side', 'County', 'State', 'Timezone', 'Airport_Code', 'Wind_Direction', 'Weather_Condition']

for i in columns:
    le.fit(data[i])
    data[i] = le.transform(data[i])


    def get_binary_column(df, column):
        return data[column].apply(lambda x: 1 if x == 'Day' else 0)

        data['Sunrise_Sunset'] = get_binary_column(data, 'Sunrise_Sunset')
data['Civil_Twilight'] = get_binary_column(data, 'Civil_Twilight')
data['Nautical_Twilight'] = get_binary_column(data, 'Nautical_Twilight')
data['Astronomical_Twilight'] = get_binary_column(data, 'Astronomical_Twilight')

data.info()

data.isnull().sum()
# data_X

scaler = StandardScaler()

data_X = scaler.fit_transform(data)

import sklearn

X_train, X_test, y_train, y_test = train_test_split(data_X, data, train_size=0.7, random_state=100)

from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import accuracy_score

clf = DecisionTreeClassifier(random_state=42)

# Train & Test
clf.fit(X_train, y_train)
y_pred = clf.predict(X_test)

# Print accuracy_entropy
print('Decision Tree accuracy_score: {:.3f}.'.format(accuracy_score(y_test, y_pred)))

from sklearn import svm

clf = svm.SVC()
clf.fit(X_train, y_train)

y_pred = clf.predict(X_test)
print('SVM accuracy_score: {:.3f}.'.format(accuracy_score(y_test, y_pred)))



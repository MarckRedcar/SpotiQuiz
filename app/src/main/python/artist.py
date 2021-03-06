import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import cv2
from PIL import Image
import io
import base64
from os.path import dirname, join

class artist:
    def __init__(self, artistName):
        self.artistName = artistName

    def plot(self):

        #definition graphic figure
        fig = plt.figure()

        #loading data
        filename = join(dirname(__file__), "libs/data.csv")
        data = pd.read_csv(filename)

        #suitable parameter setting
        artist = self.artistName.title()
        artist = "'" + artist + "'"

        #data filtering by parameter
        data = data[data['artists'].str.contains(artist)]

        #new table with two columns, index and count
        songs_by_year = pd.crosstab(index=data['year'], columns=['count_songs'], colnames=[''])

        ### PLOT FEATURES ###

        x_ticks = np.arange(data['year'].min(), data['year'].max()+1)
        y_ticks = np.arange(0, songs_by_year['count_songs'].max()+3, 3)

        clrs = ['black' if (x < songs_by_year['count_songs'].max()) else 'green' for x in songs_by_year['count_songs'] ]

        plt.bar(songs_by_year.index, songs_by_year['count_songs'], color=clrs)

        plt.xticks(x_ticks, rotation=90)
        plt.yticks(y_ticks)
        plt.ylabel('Counter Songs', fontdict={'fontweight':'bold', 'fontsize': 14})
        #plt.xlabel('Year', fontdict={'fontweight':'bold', 'fontsize': 14})
        plt.title(artist.strip("'") + ' stats', fontdict={'fontweight':'bold', 'fontsize': 18})

        ### BASE64 CODING AND SAVING IN BUFFER ###

        fig.canvas.draw()

        img = np.fromstring(fig.canvas.tostring_rgb(),dtype=np.uint8,sep='')
        img = img.reshape(fig.canvas.get_width_height()[::-1]+(3,))
        img = cv2.cvtColor(img,cv2.COLOR_RGB2BGR)

        pil_im = Image.fromarray(img)
        buff = io.BytesIO()
        pil_im.save(buff,format="PNG")
        img_str = base64.b64encode(buff.getvalue())

        return ""+str(img_str,'utf-8')

    def pie(self):

        #definition graphic figure
        fig = plt.figure()

        #loading data
        filename = join(dirname(__file__), "libs/data_w_genres.csv")
        data_w_genres = pd.read_csv(filename)

        #suitable parameter setting
        artist = self.artistName.title()

        #data filtering by parameter
        data_w_genres = data_w_genres[data_w_genres['artists'] == artist]

        #extracting genres of the first row contained in an array
        genres = data_w_genres['genres'].values[0]
        genres = genres.translate({ord(i): None for i in '[]\''})
        genres = genres.replace(', ', ',')
        genres = genres.split(',')

        #frequency for each genre extracted
        genresfreq = [genres.count(w) for w in genres]

        ### PLOT FEATURES ###

        plt.pie(genresfreq, labels=genres, startangle=-40)
        plt.axis('equal')

        my_circle = plt.Circle((0,0), 0.6, color='white')
        p = plt.gcf()
        p.gca().add_artist(my_circle)

        ### BASE64 CODING AND SAVING IN BUFFER ###

        fig.canvas.draw()

        img = np.fromstring(fig.canvas.tostring_rgb(),dtype=np.uint8,sep='')
        img = img.reshape(fig.canvas.get_width_height()[::-1]+(3,))
        img = cv2.cvtColor(img,cv2.COLOR_RGB2BGR)

        pil_im = Image.fromarray(img)
        buff = io.BytesIO()
        pil_im.save(buff,format="PNG")
        img_str = base64.b64encode(buff.getvalue())

        return ""+str(img_str,'utf-8')







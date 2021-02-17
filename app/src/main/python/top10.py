import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import cv2
from PIL import Image
import io
import base64
from os.path import dirname, join

class top10:

    def artist(self):

        #definition graphic figure
        fig = plt.figure(figsize=(15,18))

        #loading data
        filename = join(dirname(__file__), "libs/data_by_artist.csv")
        data_by_artist = pd.read_csv(filename)

        #data filtering
        data_by_artist = data_by_artist.nlargest(10, 'popularity')
        data_by_artist = data_by_artist.sort_values('artists')

        ### PLOT FEATURES ###

        clrs = ['black' if (x < data_by_artist['popularity'].max()) else 'green' for x in data_by_artist['popularity'] ]

        plt.bar(data_by_artist['artists'], data_by_artist['popularity'], color=clrs)

        plt.xticks(rotation=90)
        plt.ylabel('Popularity', fontdict={'fontweight':'bold', 'fontsize': 14})
        #plt.xlabel('Artists', fontdict={'fontweight':'bold', 'fontsize': 14})
        plt.title('Top 10 Artists', fontdict={'fontweight':'bold', 'fontsize': 18})

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

    def genre(self):

        #definition graphic figure
        fig = plt.figure(figsize=(15,18))

        #loading data
        filename = join(dirname(__file__), "libs/data_by_genres.csv")
        data_by_genres = pd.read_csv(filename)

        #data filtering
        data_by_genres = data_by_genres.nlargest(10, 'popularity')
        data_by_genres = data_by_genres.sort_values('genres')

        ### PLOT FEATURES ###

        clrs = ['black' if (x < data_by_genres['popularity'].max()) else 'green' for x in data_by_genres['popularity'] ]

        plt.bar(data_by_genres['genres'], data_by_genres['popularity'], color=clrs)

        plt.xticks(rotation=90)
        plt.ylabel('Popularity', fontdict={'fontweight':'bold', 'fontsize': 14})
        #plt.xlabel('Artists', fontdict={'fontweight':'bold', 'fontsize': 14})
        plt.title('Top 10 Genres', fontdict={'fontweight':'bold', 'fontsize': 18})

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

    def song(self):

        #definition graphic figure
        fig = plt.figure(figsize=(15,18))

        #loading data
        filename = join(dirname(__file__), "libs/data.csv")
        data = pd.read_csv(filename)

        #data filtering
        data = data.nlargest(10, 'popularity')
        data = data.sort_values('name')

        ### PLOT FEATURES ###

        clrs = ['black' if (x < data['popularity'].max()) else 'green' for x in data['popularity'] ]

        plt.bar(data['name'], data['popularity'], color=clrs)

        plt.xticks(rotation=90)
        plt.ylabel('Popularity', fontdict={'fontweight':'bold', 'fontsize': 18})
        #plt.xlabel('Artists', fontdict={'fontweight':'bold', 'fontsize': 18})
        plt.title('Top 10 Songs', fontdict={'fontweight':'bold', 'fontsize': 18})

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

    def decade(self):

        #definition graphic figure
        fig = plt.figure(figsize=(15,18))

        #loading data
        filename = join(dirname(__file__), "libs/data_by_year.csv")
        data_by_year = pd.read_csv(filename)

        #data filtering
        data_by_year['decade'] = (10 * (data_by_year['year'] // 10)).astype(str) + 's'
        data_by_year = data_by_year.groupby('decade', as_index=False)['popularity'].mean()
        data_by_year = data_by_year.nlargest(10, 'popularity')
        data_by_year = data_by_year.sort_values('decade')

        ### PLOT FEATURES ###

        clrs = ['black' if (x < data_by_year['popularity'].max()) else 'green' for x in data_by_year['popularity'] ]

        plt.bar(data_by_year['decade'], data_by_year['popularity'], color=clrs)

        plt.ylabel('Popularity', fontdict={'fontweight':'bold', 'fontsize': 14})
        #plt.xlabel('Artists', fontdict={'fontweight':'bold', 'fontsize': 14})
        plt.title('Top 10 Decades', fontdict={'fontweight':'bold', 'fontsize': 18})

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







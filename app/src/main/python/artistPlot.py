import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import cv2
from PIL import Image
import io
import base64
from os.path import dirname, join

def plot(artistName):

    #plot bar
    fig = plt.figure()
    filename = join(dirname(__file__), "libs/data.csv")
    data = pd.read_csv(filename)

    artist = artistName.title()
    artist = "'" + artist + "'"

    data = data[data['artists'].str.contains(artist)]

    songs_by_year = pd.crosstab(index=data['year'], columns=['count_songs'], colnames=[''])

    x_ticks = np.arange(data['year'].min(), data['year'].max()+1)
    y_ticks = np.arange(0, songs_by_year['count_songs'].max()+3, 3)

    clrs = ['black' if (x < songs_by_year['count_songs'].max()) else 'green' for x in songs_by_year['count_songs'] ]

    plt.bar(songs_by_year.index, songs_by_year['count_songs'], color=clrs)

    plt.xticks(x_ticks, rotation=90)
    plt.yticks(y_ticks)
    #plt.ylabel('Counter Songs', fontdict={'fontweight':'bold', 'fontsize': 14})
    #plt.xlabel('Year', fontdict={'fontweight':'bold', 'fontsize': 14})
    plt.title(artist.strip("'") + ' stats', fontdict={'fontweight':'bold', 'fontsize': 18})

    fig.canvas.draw()

    img = np.fromstring(fig.canvas.tostring_rgb(),dtype=np.uint8,sep='')
    img = img.reshape(fig.canvas.get_width_height()[::-1]+(3,))
    img = cv2.cvtColor(img,cv2.COLOR_RGB2BGR)

    pil_im = Image.fromarray(img)
    buff = io.BytesIO()
    pil_im.save(buff,format="PNG")
    img_str = base64.b64encode(buff.getvalue())

    return ""+str(img_str,'utf-8')







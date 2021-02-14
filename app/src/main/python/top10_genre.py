import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import cv2
from PIL import Image
import io
import base64
from os.path import dirname, join

def plot():

    #plot bar
    fig = plt.figure()
    filename = join(dirname(__file__), "libs/data_by_genres.csv")
    data_by_genres = pd.read_csv(filename)

    data_by_genres = data_by_genres.nlargest(10, 'popularity')
    data_by_genres = data_by_genres.sort_values('genres')

    clrs = ['black' if (x < data_by_genres['popularity'].max()) else 'green' for x in data_by_genres['popularity'] ]

    plt.bar(data_by_genres['genres'], data_by_genres['popularity'], color=clrs)

    plt.ylabel('Popularity', fontdict={'fontweight':'bold', 'fontsize': 14})
    plt.xlabel('Artists', fontdict={'fontweight':'bold', 'fontsize': 14})
    plt.title('Top 10 Genres', fontdict={'fontweight':'bold', 'fontsize': 18})

    fig.canvas.draw()

    img = np.fromstring(fig.canvas.tostring_rgb(),dtype=np.uint8,sep='')
    img = img.reshape(fig.canvas.get_width_height()[::-1]+(3,))
    img = cv2.cvtColor(img,cv2.COLOR_RGB2BGR)

    pil_im = Image.fromarray(img)
    buff = io.BytesIO()
    pil_im.save(buff,format="PNG")
    img_str = base64.b64encode(buff.getvalue())

    return ""+str(img_str,'utf-8')







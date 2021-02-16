import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import cv2
from PIL import Image
import io
import base64
from os.path import dirname, join

def plot(genreName):

    #definition graphic figure
    fig = plt.figure(figsize=(15,18))

    #loading data
    filename = join(dirname(__file__), "libs/data_w_genres.csv")
    data_w_genres = pd.read_csv(filename)

    #suitable parameter setting
    genre = genreName.lower()
    genre = "'" + genre + "'"

    #data filtering by parameter
    data_w_genres = data_w_genres[data_w_genres['genres'].str.contains(genre)]
    data_w_genres = data_w_genres.nlargest(10, 'popularity')
    data_w_genres = data_w_genres.sort_values('artists')

    ### PLOT FEATURES ###

    clrs = ['black' if (x < data_w_genres['popularity'].max()) else 'green' for x in data_w_genres['popularity'] ]

    plt.bar(data_w_genres['artists'], data_w_genres['popularity'], color=clrs)

    plt.xticks(rotation=90)
    plt.ylabel('Popularity', fontdict={'fontweight':'bold', 'fontsize': 14})
    #plt.xlabel('Artists', fontdict={'fontweight':'bold', 'fontsize': 14})
    plt.title('Popular ' + genre.strip("'") + ' artists' , fontdict={'fontweight':'bold', 'fontsize': 18})

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







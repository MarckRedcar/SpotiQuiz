import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import cv2
from PIL import Image
import io
import base64
from os.path import dirname, join

def pie(artistName):

    #definition graphic figure
    fig = plt.figure()

    #loading data
    filename = join(dirname(__file__), "libs/data_w_genres.csv")
    data_w_genres = pd.read_csv(filename)

    #suitable parameter setting
    artist = artistName.title()

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
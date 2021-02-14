import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import cv2
from PIL import Image
import io
import base64
from os.path import dirname, join

def pie(artistName):

    #pie plot
    fig = plt.figure()
    filename = join(dirname(__file__), "libs/data_w_genres.csv")
    data_w_genres = pd.read_csv(filename)

    artist = artistName.title()

    data_w_genres = data_w_genres[data_w_genres['artists'] == artist]

    genres = data_w_genres['genres'].values[0]
    genres = genres.translate({ord(i): None for i in '[]\''})
    genres = genres.replace(', ', ',')
    genres = genres.split(',')

    genresfreq = [genres.count(w) for w in genres] # a list comprehension

    fig, ax = plt.subplots(figsize=(9, 6), subplot_kw=dict(aspect="equal"))

    wedges, texts = ax.pie(genresfreq, wedgeprops=dict(width=0.5), startangle=-40)

    bbox_props = dict(boxstyle="square,pad=0.3", fc="w", ec="k", lw=0.72)
    kw = dict(arrowprops=dict(arrowstyle="-"),
              bbox=bbox_props, zorder=0, va="center")

    for i, p in enumerate(wedges):
        ang = (p.theta2 - p.theta1)/2. + p.theta1
        y = np.sin(np.deg2rad(ang))
        x = np.cos(np.deg2rad(ang))
        horizontalalignment = {-1: "right", 1: "left"}[int(np.sign(x))]
        connectionstyle = "angle,angleA=0,angleB={}".format(ang)
        kw["arrowprops"].update({"connectionstyle": connectionstyle})
        ax.annotate(genres[i], xy=(x, y), xytext=(1.35*np.sign(x), 1.4*y),
                    horizontalalignment=horizontalalignment, **kw)

    fig.canvas.draw()

    img = np.fromstring(fig.canvas.tostring_rgb(),dtype=np.uint8,sep='')
    img = img.reshape(fig.canvas.get_width_height()[::-1]+(3,))
    img = cv2.cvtColor(img,cv2.COLOR_RGB2BGR)

    pil_im = Image.fromarray(img)
    buff = io.BytesIO()
    pil_im.save(buff,format="PNG")
    img_str = base64.b64encode(buff.getvalue())

    return ""+str(img_str,'utf-8')
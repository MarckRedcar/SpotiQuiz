import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import cv2
from PIL import Image
import io
import base64
from os.path import dirname, join

def plot(decadeName):

    #plot bar
    fig = plt.figure()
    filename = join(dirname(__file__), "libs/data.csv")
    data = pd.read_csv(filename)

    decade = decadeName

    data['decade'] = (10 * (data['year'] // 10)).astype(str) + 's'
    data = data[data['decade'] == decade]
    data = data.groupby('artists', as_index=False)['popularity'].mean()
    data = data.nlargest(10, 'popularity')
    data = data.sort_values('artists')

    clrs = ['black' if (x < data['popularity'].max()) else 'green' for x in data['popularity'] ]

    plt.bar(data['artists'], data['popularity'], color=clrs)

    plt.xticks(rotation=90)
    plt.ylabel('Popularity', fontdict={'fontweight':'bold', 'fontsize': 14})
    plt.xlabel('Artists', fontdict={'fontweight':'bold', 'fontsize': 14})
    plt.title('Popular artists of ' + decade , fontdict={'fontweight':'bold', 'fontsize': 18})

    fig.canvas.draw()

    img = np.fromstring(fig.canvas.tostring_rgb(),dtype=np.uint8,sep='')
    img = img.reshape(fig.canvas.get_width_height()[::-1]+(3,))
    img = cv2.cvtColor(img,cv2.COLOR_RGB2BGR)

    pil_im = Image.fromarray(img)
    buff = io.BytesIO()
    pil_im.save(buff,format="PNG")
    img_str = base64.b64encode(buff.getvalue())

    return ""+str(img_str,'utf-8')
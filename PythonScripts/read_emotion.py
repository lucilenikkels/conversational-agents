from threading import Thread

import flask
import tensorflow as tf
import numpy as np
import cv2
import time
import matplotlib.pyplot as plt

from vggface_4096x4096x2 import VGGFace

app = flask.Flask(__name__)
app.config["DEBUG"] = True

cap = cv2.VideoCapture(0)

BATCH_SIZE = 1
DIMENSION_SIZE = 96
MODEL_PATH = 'trained_model/model.ckpt-975'


def get_image():
    _, image_np = cap.read()
    gray = cv2.cvtColor(image_np, cv2.COLOR_BGR2GRAY)
    face_cascade = cv2.CascadeClassifier('resources/face_detection.xml')
    faces = face_cascade.detectMultiScale(gray, 1.1, 4)
    x, y, w, h = faces[0]
    cropped_image = image_np[y:y+h, x:x+w]

    # Show Webcam image
    # cv2.imshow('object detection', cropped_image)
    # cv2.waitKey(0)

    images = tf.convert_to_tensor(np.expand_dims(cropped_image, 0), dtype=tf.float32)
    images = tf.image.resize_images(images, tf.convert_to_tensor([DIMENSION_SIZE, DIMENSION_SIZE]))

    # Normalize and rescale image
    input_tensor = tf.to_float(images)
    input_tensor = tf.reshape(input_tensor, [-1, 96, 96, 3])
    input_tensor -= 128
    input_tensor /= 128

    # Show Tensor image
    # plt.imshow(tf.Session().run(images / 256)[0])
    # plt.show()

    return input_tensor

    # input_tensor = []
    # for file_path in ['lach_1', 'lach_2', 'lach_3', 'lach_4', 'boos_1', 'boos_2', 'boos_3', 'boos_4']:
    #     image = tf.read_file('resources/{}.jpg'.format(file_path))
    #     image = tf.image.decode_jpeg(image, channels=3)
    #     image = tf.image.resize_images(image, tf.convert_to_tensor([DIMENSION_SIZE, DIMENSION_SIZE]))
    #     input_tensor.append(image)


def evaluate():
    # Setup Network
    network = VGGFace(BATCH_SIZE)
    network.setup(get_image())

    # Load model into new session
    sess = tf.Session()
    saver = tf.train.Saver()
    saver.restore(sess, MODEL_PATH)
    tf.train.start_queue_runners(sess=sess)

    # Evaluate the image in the network
    result = sess.run(network.get_output())
    result_str = '{} {}'.format(result[0][0], result[0][1])

    return result_str


@app.route('/get_emotion', methods=['GET'])
def emotion():
    return evaluate()


if __name__ == '__main__':
    app.run()

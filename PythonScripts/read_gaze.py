import numpy as np

with open("gaze_distribution.txt", 'r') as file:
    line = ""
    while "kijkrichting spreker1" not in line:
        line = file.readline()

    # Skip random numbers
    file.readline()
    file.readline()
    file.readline()

    listener_gaze_durations = []
    random_gaze_durations = []

    while True:
        line = file.readline()
        if "IntervalTier" in line:
            break

        startTime = float(line)
        line = file.readline()
        endTime = float(line)
        duration = endTime - startTime
        line = file.readline()

        if "x" in line:
            random_gaze_durations.append(duration)
        if "g" in line:
            listener_gaze_durations.append(duration)

    # print(listener_gaze_durations)
    # print(random_gaze_durations)

    listener_gaze_mean = np.mean(listener_gaze_durations)
    listener_gaze_std = np.std(listener_gaze_durations)
    random_gaze_mean = np.mean(random_gaze_durations)
    random_gaze_std = np.std(random_gaze_durations)

    print("Gazing at listener, mean duration: ", listener_gaze_mean, ", std duration ", listener_gaze_std)
    print("Gazing away, mean duration: ", random_gaze_mean, ", std duration ", random_gaze_std)

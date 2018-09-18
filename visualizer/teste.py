from random import random
from reader import OutputReader

_file = '/home/chronius/disciplinas/2018.1/tcc/workspace/visualizer/environment01.xml'
reader = OutputReader(_file)#'teste.xml')



def plot_visualization(step):
    walls, taxis, drops, picks, passengers = reader.getRepresentationPlot(step)
    print("taxi on ", taxis)
    # plot walls
    x0, y0, x1, y1 = [list(x) for x in zip(*walls)]

    with self.canvas:
        Color(*self.wall_color, mode='hsv')
        
        for wall in walls:
            wall = [w * 100 for w in wall]
            Line(points=wall, width=5)

    # plot taxis
    with self.canvas:
        Color(*self.taxi_color, mode='hsv')
        
        for taxi in taxis:
            Ellipse(pos=(taxi[0]*100+30, taxi[1]*100-180), size=(50, 50))

    # plot picks
    with self.canvas:
        Color(*self.pick_color, mode='hsv')

        for pick in picks:
            x1, y1 = pick
            x2 = x1+1
            y2 = y1
            x3 = x1 + 0.5
            y3 = y1-1
            points = [x1, y1, x2, y2, x3, y3]
            points = [100 * p for p in points]
            Triangle(points=points)

    #plot drops
    with self.canvas:
        Color(*self.drop_color, mode='hsv')
        for drop in drops:
            x1, y1 = drop
            x2 = x1+1
            y2 = y1
            x3 = x1 + 0.5
            y3 = y1-1
            points = [x1, y1, x2, y2, x3, y3]
            points = [100 * p for p in points]
            Triangle(points=points)

    #plot passenger
    with self.canvas:
        Color(*self.passenger_color, mode='hsv')
        for passenger in passengers:
            Ellipse(pos=(passenger[0]*100+50, passenger[1]*100-50), size=(25, 25))


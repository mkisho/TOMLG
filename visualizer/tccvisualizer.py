from random import random
from kivy.app import App
from kivy.uix.widget import Widget
from kivy.uix.button import Button
from kivy.graphics import Color, Ellipse, Line, Triangle

from reader import OutputReader

_file = '/home/chronius/disciplinas/2018.1/tcc/workspace/visualizer/environment01.xml'
reader = OutputReader(_file)#'teste.xml')


class MyPaintWidget(Widget):
    wall_color = (0.4, 0.3, 0.2)
    taxi_color = (0.2, 0.1, 0.2)
    passenger_color = (0.3, 0.5, 1)
    pick_color = (1, 0, 1)
    drop_color = (0, 0.7, 0.3)
    step=0

    def on_touch_down(self, touch):
        self.canvas.clear()
        self.step += 1
        self.plot_step(step=self.step)


    def plot_step(self, step=0):
        print("on step ", step)
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

                
class MyPaintApp(App):

    def build(self):
        parent = Widget()
        self.painter = MyPaintWidget()
        self.painter.plot_step()
        clearbtn = Button(text='Next')
        clearbtn.bind(on_release=self.clear_canvas)
        parent.add_widget(self.painter)
        #parent.add_widget(clearbtn)
        return parent

    def clear_canvas(self, obj):
        self.painter.canvas.clear()


if __name__ == '__main__':
    MyPaintApp().run()

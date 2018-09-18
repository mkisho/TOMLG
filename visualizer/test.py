import sys
import os
import svgwrite
from reader import OutputReader

BOARD_WIDTH = "10cm"
BOARD_HEIGHT = "10cm"
BOARD_SIZE = (BOARD_WIDTH, BOARD_HEIGHT)
CSS_STYLES = """
    .background { fill: lavenderblush; }
    .line { stroke: firebrick; stroke-width: .1mm; }
    .blacksquare { fill: #06ccb1; }
    .whitesquare { fill: #047a6a; }
    .walls { fill: #0f0f0f;}
"""

def draw_board(dwg, max_x=8, max_y=8):
    def group(classname):
        return dwg.add(dwg.g(class_=classname))
    
    lines = group("line") 
    white_squares = group("whitesquare")
    black_squares = group("blacksquare")
    
    # draw lines
    for i in range(max_x+1):
        y = i * 10
        lines.add(dwg.line(start=(0, y), end=(max_x*10, y)))
        x = i * 10
        lines.add(dwg.line(start=(x, 0), end=(x, max_y*10)))
        
    # draw squares
    for x in range(max_x):
        for y in range(max_y):
            xc = x * 10 + 1
            yc = y * 10 + 1
            square = dwg.rect(insert=(xc, yc), size=(8, 8))
            (white_squares if (x+y) % 2 else black_squares).add(square)



_file = '/home/chronius/disciplinas/2018.1/tcc/workspace/visualizer/environment01.xml'
reader = OutputReader(_file)#'teste.xml')

def draw_step(dwg, step=1):
    def group(classname):
        return dwg.add(dwg.g(class_=classname))

    walls = group('walls')
    objs  = group('objs')

    data = reader.getRepresentationPlot(step)
    print("taxi on ", data['taxis'])
    # plot walls

    for wall in data['walls']['vertical']:
        x = wall[0]
        y0  = wall[1]
        yf = wall[3]

        if(y0 > yf):
            y0, yf = yf, y0
        
        walls.add(dwg.rect(insert=(x*10-1, y0*10), size=(2, (y0 - yf)*10)))
        
    for wall in data['walls']['horizontal']:
        x0 = wall[0]
        y  = wall[1]
        xf = wall[2]

        if(x0 > xf):
            x0, xf = xf, x0     
        walls.add(dwg.rect(insert=(x0*10, y*10-1), size=((x0 - xf)*10, 2)))

    for taxi in data['taxis']:
        if(taxi[2]):
            objs.add(dwg.image('taxiPassenger.svg',insert=(taxi[0]*10, taxi[1]*10), size=(9, 9)))    
        else:
            objs.add(dwg.image('taxi.svg',insert=(taxi[0]*10, taxi[1]*10), size=(9, 9)))    

    for passenger in data['passenger']:
        if(not passenger[2]):
            objs.add(dwg.image('passenger.svg',insert=(passenger[0]*10, passenger[1]*10), size=(9, 9)))    

    for pick in data['picks']:
        objs.add(dwg.image('pickup.svg',insert=(pick[0]*10, pick[1]*10), size=(9, 9)))    

    for drop in data['drops']:
        objs.add(dwg.image('drop.svg',insert=(drop[0]*10, drop[1]*10), size=(9, 9)))    

            
   
def main():
    for step in range(reader.getNumMinds()):
        dwg = svgwrite.Drawing('simulation/step{}.svg'.format(step))
        dwg.defs.add(dwg.style(CSS_STYLES))
        dwg.add(dwg.rect(size=(80,80), class_='background'))
        draw_board(dwg)
        draw_step(dwg, step=step)
        
        dwg.save()

if __name__== '__main__':
    main()

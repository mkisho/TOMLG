import xml.etree.ElementTree as ET


class OutputReader():

    def __init__(self, file_name):
        self.tree = ET.parse(file_name)
        self.root = self.tree.getroot()
        self.num_minds = len([k for k in self.root.iter('Mind')])

        self.width = 10
        self.height = 10

    def getNumMinds(self):
        return self.num_minds

    def getMind(self, n):
        for element in self.root.iter('Mind'):
            if(element.attrib['step'] == str(n)):
                return element
                
        return None

    def __extract_attributes__(self, attribNode):
        atribs = {}
        for attribute in attribNode.findall('Att'):
            atribs[attribute.attrib['name']] = attribute.attrib['value'] 
        
        return atribs

    def getBeliefs(self, n):
        beliefs = dict()
        mind = self.getMind(n)
        pfs =  [k for k in mind.find("Beliefs").find("EnvironmentBeliefs").iter('PF')]

        beliefs["perception"] = []
        for pf in pfs:
            beliefs["perception"].append(pf.attrib)

        return beliefs

    def getRepresentationPlot(self, n):
        mind = self.getMind(n)

        objs = [k for k in mind.find("Beliefs").find("EnvironmentBeliefs").iter('Obj')]
        pfs =  [k for k in mind.find("Beliefs").find("EnvironmentBeliefs").iter('PF')]


        data = dict()
        data["walls"] = dict()
        data["walls"]["horizontal"] = []
        data["walls"]["vertical"] = []
        data['taxis'] = []
        data['picks'] = []
        data['drops'] = []
        data['passenger'] = []

        for obj in objs:
            attributes = self.__extract_attributes__(obj.find('Attributes'))
            #print(attributes)
            if obj.attrib['class'] == 'horizontalWall':
                #import pdb
                #pdb.set_trace()
                x0 = int(attributes['leftStartOfWall'])
                y0 = int(attributes['wallOffSet'])
                x1 = int(attributes['rightStartOfWall'])
                y1 = int(attributes['wallOffSet'])
                data['walls']['horizontal'].append((x0, y0, x1, y1))
            elif obj.attrib['class'] == 'verticalWall':
                y0 = int(attributes['topOfWall'])
                x0 = int(attributes['wallOffSet'])
                y1 = int(attributes['bottomOfWall'])
                x1 = int(attributes['wallOffSet'])
                data['walls']['vertical'].append((x0, y0, x1, y1))
            elif obj.attrib['class'] == 'taxi':
                x = int(attributes['xLocation'])
                y = int(attributes['yLocation'])
                passengerIn = True if attributes['passengerInTaxi']  !='falses' else False
                data['taxis'].append((x, y, passengerIn))
            elif obj.attrib['class'] == 'passenger':
                x = int(attributes['xLocation'])
                y = int(attributes['yLocation'])
                inTaxi = True if(attributes['inTaxi'] != 'falses') else False
                data['passenger'].append((x, y, inTaxi))
            elif obj.attrib['class'] == 'goalLocation':
                x = int(attributes['xLocation'])
                y = int(attributes['yLocation'])
                if attributes['goalType'] == 'PickUp':
                    data['picks'].append((x, y))
                else:
                    data['drops'].append((x, y))

        return data


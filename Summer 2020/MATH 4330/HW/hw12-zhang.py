# hw12.py
# Chris Monico, 7/29/19

import tkinter as tk
import cmath
import math


def Iterate(c, N):
    # Problem 1 is to write this function.
    #
    # Consider the sequence of complex numbers defined by
    # z_0 = 0,
    # z_{n+1} = z_n^2 + c.
    # Start computing this sequence until we either encounter
    # and element with |z_k| > 100 or we reach z_N.
    # Return the number of elements which were computed.
    n = 0
    xn = 0
    for n in range(int(N)):
        Xn = xn**2 + c
        xn = Xn
        n = n + 1
        if (abs(xn) > 100):
            break
    return n



def colorFromRGB(r, g, b):
    # R, G, B are floating point numbers between 0 and 1 describing
    # the intensity level of the Red, Green and Blue components.
    X = [int(r*255), int(g*255), int(b*255)]
    for i in range(3):
        if X[i]<0:
            X[i] = 0
        if X[i]>255:
            X[i]=255
    color = "#%02x%02x%02x"%(X[0],X[1],X[2])
    return color



class MyApplication(tk.Frame):
    def __init__(self, master, centerX, centerY, width, height):
        super().__init__(master)
        self.master = master
        self.canvasW = 800
        self.canvasH = 600
        self.w = tk.Canvas(self.master, width=self.canvasW, height=self.canvasH)
        self.w.pack()
        self.maxIts = 50.0
        self.TL_x = centerX - width/2.0
        self.TL_y = centerY + height/2.0
        self.BR_x = centerX + width/2.0
        self.BR_y = centerY - height/2.0
        self.drawingInProgress = False
        self.master.bind('<KeyPress>', self.keydown)
        self.master.bind('<KeyRelease>', self.keyup)

    def keydown(self, e):
        # If there is a drawing in progress, ignore the event.
        if self.drawingInProgress:
            return
        
        w = self.BR_x - self.TL_x
        h = self.TL_y - self.BR_y
        cx = self.TL_x + w/2.0
        cy = self.BR_y + h/2.0
        print("Currently: %f x %f, centered at %f,%f" % (w,h,cx,cy))
        if e.keysym == 'Up':
            cy += h/3.0
        if e.keysym == 'Down':
            cy -= h/3.0
        if e.keysym == 'Left':
            cx -= w/3.0
        if e.keysym == 'Right':
            cx += w/3.0
        if e.keysym == 'equal': # Zoom in
            w *= 0.75 
            h *= 0.75
        if e.keysym == 'minus': # Zoom out
            w *= 4.0/3.0
            h *= 4.0/3.0
        # Recompute the coordinates of the top-left corner and bottom right corner.
        self.TL_x = cx - w/2.0
        self.TL_y = cy + h/2.0
        self.BR_x = cx + w/2.0
        self.BR_y = cy - h/2.0
        
        # Clear the canvas and free associated memory:
        self.w.delete("all")
        # And re-draw
        self.update_screen()
        self.drawScreen()

    def keyup(self, e):
        pass

    def update_screen(self):
        self.master.update_idletasks()
        self.master.update()

    def drawScreen(self):
        self.drawIt()

    def label_screen(self, color):
        self.w.create_text(0, 0, text="(%f,%f)"%(self.TL_x,self.TL_y),anchor="nw",fill=color) 
        self.w.create_text(self.canvasW-1, self.canvasH-1, text="(%f,%f)"%(self.BR_x,self.BR_y),anchor="se",fill=color)
        
    def plotPoint(self,x, y, color):
        
        # Convert the Cartesian (x,y) point to a canvas value:
        cartesianWidth = self.BR_x - self.TL_x
        cartesianHeight = self.TL_y - self.BR_y
        x1 = self.canvasW*(x-self.TL_x)/cartesianWidth
        y1 = self.canvasH*(1.0 - (y-self.BR_y)/cartesianHeight)
        self.w.create_rectangle(x1-0.48, y1-0.48, x1+0.48, y1+0.48, fill=color,outline=color)

    def pixelW(self):
        # Gives the width of each pixel in Cartesian coordinates.
        return (self.BR_x - self.TL_x)/float(self.canvasW)

    def pixelH(self):
        # Gives the height of each pixel in Cartesian coordinates.
        return (self.TL_y - self.BR_y)/float(self.canvasH)

    def canvas_width(self):
        return self.canvasW
    def canvas_height(self):
        return self.canvasH

    def canvas_to_cartesian(self, pixelX, pixelY):
        # Returns Cartesian coordinates associated with (a corner of)
        # the given pixel.
        x = self.TL_x + pixelX * self.pw;
        y = self.BR_y + pixelY * self.ph
        return x,y

    def drawIt(self):
        w = self.canvasW
        h = self.canvasH
        self.pw = self.pixelW()
        self.ph = self.pixelH()

        self.drawingInProgress = True # Ignore events until this is done.
        ##############################################################
        # Loop over all the pixels in the plot. Determine a color for
        # each pixel and plot it.
        ##############################################################
        for i in range(w):
            for j in range(h):
                # Get the cartesian coordinates (x,y) of the pixel at
                # position (i,j) in the window. (Don't need to change)
                x,y = self.canvas_to_cartesian(i, j)

                # Do some calculation that tells me how to color the
                # point (x,y):
                color = self.getPointColor(x, y)


                # Plot the point.
                self.plotPoint(x,y,color)

            if (i%20==0):
                # This will update the actual on-screen window. We only do
                # it once in a while because it is a relatively slow operation.
                # If we did it after each individual pixel it would slow
                # things down a lot and you might not have that kind of time to wait.
                self.update_screen()
        self.drawingInProgress = False
        
        # Draw coordinate labels on the image so we can tell what the scale is.
        self.label_screen("white");


    def getPointColor(self,x, y):
        # Do something to figure out what color the point (x,y)
        # should be painted. Do some calculations to get Red, Green, Blue
        # intensities which are mostly in the interval [0,1].


        k = Iterate(x+y*1j, self.maxIts)
          
        
        # Choose a color that depends on k.
        if k==self.maxIts:
            return colorFromRGB(0,0,0)
        t = (k/float(self.maxIts))*2.0*3.1415926535
        R = (1.0+math.cos(1.0+t))/2.0
        G = (1.0+math.cos(1.0+t+2.0*3.1415926/3.0))/2.0
        B = (1.0+math.cos(1.0+t+4.0*3.1415926/3.0))/2.0
    
        color = colorFromRGB(R,G,B)
        return color


root = tk.Tk()
app = MyApplication(root,0,0,4,3)
app.drawScreen()
app.mainloop()

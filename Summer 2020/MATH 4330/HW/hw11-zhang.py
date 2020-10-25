# hw11.py
# Chris Monico, 7/23/19
# Graphical illustration of Newton's Method for functions
# from C to C.

import tkinter as tk
import cmath
import math

def Newton(f, df, z0, epsilon):
    n = 0
    xn = z0
    if abs(df(xn)) < epsilon:
        #print("it may lead to divide by zero")
        return None
    while abs(f(xn)) >= epsilon:
        Xn = xn - f(xn) / df(xn)
        xn = Xn
        if n == 25 or abs(df(xn)) < epsilon:
            #print()
            return None
        n = n + 1
    return xn

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

def color_from_comp(z):
    x = z.real*2.7
    y = z.imag*2.7
    r = 0.5*(1.0 + math.tanh(x))
    g = 0.5*(1.0 + math.sin(x*y))
    b = 0.5*(1.0 + math.cos(x+y))
    return colorFromRGB(r, g, b)

def F(z):
    return z**5 - 1

def dF(z):
    return 5*z**4


class MyApplication(tk.Frame):
    def __init__(self, master):
        super().__init__(master)
        self.master = master
        self.pack()
        self.center = 0+0j
        self.cartesian_width = 4
        self.screen_width = 800
        self.screen_height = 600

        # Complex coordinates corresponding to the top-left and bottom
        # right corners of the window.
        w = self.cartesian_width
        ar = self.screen_width / self.screen_height
        self.top_left = self.center - w/2 + ((w/2)/ar)*(1j)
        self.bottom_right = self.center + w/2 - ((w/2)/ar)*(1j)
        self.create_widgets()
        
        print(self.center)
        print(self.screen_width)
        print(self.screen_height)
        print(self.top_left)
        print(self.bottom_right)

    def update_screen(self):
        self.master.update_idletasks()
        self.master.update()


    def create_widgets(self):
        # First, a Canvas widget that we can draw on. It will be 800 pixels wide,
        # and 600 pixels tall.
        self.canvas = tk.Canvas(self.master, width=self.screen_width, height=self.screen_height, background="white")
        # This 'pack' method packs it into the top-level window.        
        self.canvas.pack()


    def comp_from_pixel(self, x, y):
        # Return the complex number corresponding to the center of the
        # given pixel.
        # Problem 2 is to write this function
        d_x = self.bottom_right.real - self.top_left.real
        d_y = self.top_left.imag - self.bottom_right.imag
        x_pos = self.top_left.real + x*(d_x/self.screen_width)
        y_pos = self.top_left.imag - y*(d_y/self.screen_height)
        z = complex(x_pos,y_pos)
        return z


        
    def draw_pixel(self, x, y, C):
        self.canvas.create_rectangle(x-0.5, y-0.5, x+0.4, y+0.4, fill=C, outline=C)

    def draw_newton_plot(self):
        for i in range(self.screen_width):
            for j in range(self.screen_height):
                z = self.comp_from_pixel(i, j)
                root = Newton(F, dF, z, 0.000001)
                try:
                    color = color_from_comp(root)
                except:
                    color="black"
                self.draw_pixel(i, j, color)
            if i%20 == 0:
                self.update_screen()



root = tk.Tk()
app = MyApplication(master=root)
app.draw_newton_plot()
app.mainloop()

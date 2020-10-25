# Chen Zhang
# 6/22/2020
# hw10-zhang.py

import tkinter as tk

class MyApplication(tk.Frame):
    ##########################################
    def __init__(self, master):
        super().__init__(master)
        self.master = master
        self.pack()
        self.handle_list = []
        self.create_widgets()

    ##########################################
    def create_widgets(self):
        self.canvas = tk.Canvas(self.master, width=800, height=600, background="white")       
        self.canvas.pack()
        self.draw_button = tk.Button(text="Draw", command=self.draw)
        # Pack the button into the window.
        self.draw_button.pack()

        self.clear_button = tk.Button(text="Clear", command=self.clear)
        self.clear_button.pack()
        
    def draw(self):
        self.drawCircle(250,150,550,450,"yellow")
        self.drawRect(310,200,60,20)
        self.drawRect(430,200,60,20)
        self.drawCircle(330,240,350,260,"black")
        self.drawCircle(450,240,470,260,"black")
        self.drawRect(400,350,60,20)

    def drawCircle(self,x,y,x2,y2,color):
        h = self.canvas.create_oval(x,y,x2,y2,fill=color)
        self.handle_list.append(h)

    def drawRect(self,x,y,w,h):
        h = self.canvas.create_polygon(x,y,x+w,y,x+w,y+h,x,y+h,fill="black")
        self.handle_list.append(h)
                
    ##############################################
    def clear(self):
        while len(self.handle_list)>0:
            h = self.handle_list.pop()
            self.canvas.delete(h)

root = tk.Tk()
app = MyApplication(master=root)
app.mainloop()

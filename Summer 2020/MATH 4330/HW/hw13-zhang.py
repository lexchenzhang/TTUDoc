# hw13.py
# Chris Monico, 7/29/19

import tkinter as tk
import math

##########################################################
class GOL:
    def __init__(self, filename):
        self.board = {}
        self.rows = 0
        self.cols = 0
        self.generation = 1
        try:
            infile = open(filename, "r")
        except:
            print("Could not open file '%s' for reading!" % (filename))
            return
        i = 0 # This will be the index of the row we're currently reading from file.
        for line in infile:
            thisRow = line.strip() # Removing trailing newline character.
            N = len(thisRow)
            # Bookkeeping: the grid size will be the minimum necessary
            # to accomodate all of the nonempty lines.
            if N>self.cols:
                self.cols = N
            if N>0:
                self.rows = i+1
                
            for j in range(N):
                self.board[i,j]=0
                if thisRow[j]=='1':
                    self.board[i,j]=1
            i += 1
        infile.close()

    def neighbors(self, i, j):
        # Problem 1 is to write this method.
        c = 0 #this is the counter of neighbors.
        tem_x = i-1
        if tem_x < 0:
            tem_x = tem_x + self.rows
        tem_y = j-1
        if tem_y < 0:
            tem_y = tem_y + self.cols
        x_start = tem_x
        y_start = tem_y
        #for a in range(x_start, x_start + 3):
            #for b in range(y_start, y_start + 3):
                # if index equals the node itself skip it
                #if (a%self.rows==i and b%self.cols==j):
                    #continue
                # print("%d %d %d"%(a%8,b%8,self.board[a%8,b%8]))
                #if self.board[a%self.rows,b%self.cols]==1:
                    #c=c+1
        for a in range(x_start, x_start + 3):
            for b in range(y_start, y_start + 3):
                c += self.board[a%self.rows,b%self.cols]
        c -= self.board[i, j]
        return c
    
    def nextGeneration(self):
        # Problem 2 is to write this method.
        newBoard = {}
        # iterate all the node
        for i in range(self.rows):
            for j in range(self.cols):
                num = self.neighbors(i,j)
                if (self.isAlive(i,j)):
                    if (num==0 or num==1):
                        newBoard[i,j] = 0
                    elif (num==2 or num==3):
                        newBoard[i,j] = 1
                    elif (num>3):
                        newBoard[i,j] = 0
                else:
                    if (num==3):
                        newBoard[i,j] = 1
                    else:
                        newBoard[i,j] = 0
        self.board = newBoard

    def printData(self):
        for i in range(8):
            for j in range(8):
                print("%d %d %d"%(i,j,self.board[i,j]))

    def numRows(self):
        return self.rows

    def numCols(self):
        return self.cols

    def isAlive(self, row, col):
        if (row>=0) and (row<self.rows) and (col>=0) and (col<self.cols):
            if self.board[row,col]==1:
                return True
        return False

    def generation(self):
        return self.generation

   
##################################################################
class MyApplication(tk.Frame):
    def __init__(self, master, centerX, centerY, width, height):
        super().__init__(master)
        self.master = master
        self.G = GOL("input.txt")
        self.create_widgets()
        

    def create_widgets(self):
        self.canvasW = 800
        self.canvasH = 600
        self.w = tk.Canvas(self.master, width=self.canvasW, height=self.canvasH)
        self.w.pack()
        
        tk.Button(text="Advance 1 generation", command=lambda:self.drawScreen(1)).pack()
        tk.Button(text="Advance 10 generations", command=lambda:self.drawScreen(10)).pack()
        tk.Button(text="neighbors", command=lambda:self.get_neighbor_num()).pack()
        tk.Button(text="print", command=lambda:self.G.printData()).pack()
        self.drawScreen(0) # Draw the first generation


    def get_neighbor_num(self):
        print(self.G.neighbors(0,5))


    def update_screen(self):
        self.master.update_idletasks()
        self.master.update()


    def clear_screen(self, color):
        # First destroy the previous canvas widget to free up the
        # associated memory.
        self.w.destroy()
        self.w = tk.Canvas(self.master, width=self.canvasW, height=self.canvasH)
        self.w.pack()
        # Now fill the entire screen with a solid color
        self.w.create_rectangle(0,0,self.canvasW-1,self.canvasH-1,fill=color)

    def draw_grid(self, rowHeights, columnWidths, color):
        W = self.canvasW
        H = self.canvasH
        # First draw the vertical lines
        c=1
        while (c < W):
            self.w.create_line(c,0,c,H-1,fill=color)
            c += columnWidths
        # Now the horizontal
        r=0
        while (r<H):
            self.w.create_line(0,r,W-1,r,fill=color)
            r += rowHeights

    def label_screen(self, color, label):
        self.w.create_text(0, 0, text=label,anchor="nw",fill=color) 
        
    def plotPoint(self,x, y, color):
        self.w.create_rectangle(x-0.5, y-0.5, x+0.5, y+0.5, fill=color,outline=color)

    def plotCell(self, r, c, height, width,color):
        padW = 0.1*width
        padH = 0.1*height
        self.w.create_oval(c*width+padW, r*height+padH, (c+1)*width-padW, (r+1)*height-padH, fill=color,outline=color)

    def pixelW(self):
        # Gives the width of each pixel in Cartesian coordinates.
        return (self.BR_x - self.TL_x)/float(self.cavasW)

    def pixelH(self):
        # Gives the height of each pixel in Cartesian coordinates.
        return (self.TR_y - self.BL_y)/float(self.canvasH)

    def canvas_width(self):
        return self.canvasW
    def canvas_height(self):
        return self.canvasH

    def canvas_to_cartesian(self, pixelX, pixelY):
        # Returns Cartesian coordinates associated with (a corner of)
        # the given pixel.
        x = self.TL_x + (self.BR_x - self.TL_x)*pixelX/float(self.canvasW)
        y = self.BR_y + (self.TL_y - self.BR_y)*pixelY/float(self.canvasH)
        return [x,y]

    def drawScreen(self, n):
        # This function redraws the Game of Life screen. If n=0,
        # the screen is just drawn once. But if n > 0, we will
        #      (1) update the GOL once,
        #      (2) redraw the screen,
        # and repeat these steps n-1 more times (e.g., n times total).

        w = self.canvas_width()
        h = self.canvas_height()

        r = self.G.numRows()
        c = self.G.numCols()
        if (r <=0) or (c <=0):
            return # There's nothing to draw!

        # Figure out how wide each row and column should be to
        # fit nicely in the window.
        rowheight = h/r
        colwidth  = w/c
        aliveColor = colorFromRGB(1.0, 1.0, 1.0)
        deadColor = colorFromRGB(0.0, 0.0, 0.0)
        gridColor = colorFromRGB(0,0,1.0)

        done = False
        updates = 0
        while not(done):
            if (updates < n):
                self.G.nextGeneration()
                updates += 1
            self.clear_screen(deadColor)
            self.draw_grid(rowheight, colwidth, gridColor)
            for j in range(c):
                for i in range(r):
                    if self.G.isAlive(i,j):
                        self.plotCell(i, j, rowheight, colwidth, aliveColor)
            self.update_screen()
        
            # Draw the generation label on the screen.
            genColor = colorFromRGB(1.0, 0.5, 0.5)
            self.label_screen(genColor, "Generation: "+str(self.G.generation))
            if updates >= n:
                done = True




 
########################################################
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








root = tk.Tk()
app = MyApplication(root,0,0,4,3)
app.mainloop()


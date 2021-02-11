# tk-canvas-example.py
# Chris Monico, 7/23/19
# A simple example to demonstrate basic usage of tkinter.

import tkinter as tk


# Define a class for our application,
# which inherits from tk.Frame.
class MyApplication(tk.Frame):
    ##########################################
    def __init__(self, master):
        super().__init__(master)
        self.master = master
        self.pack()
        # self.handle_list is to remember handles
        # to some of the things we draw,
        # so that we can erase them later.
        self.handle_list = []
        # Create all the widgets we want in
        # our window at the beginning.
        self.create_widgets()

    ##########################################
    def create_widgets(self):
        # Create the widgets we want our window to have at startup.

        # First, a Canvas widget that we can draw on.
        # It will be 800 pixels wide, and 600 pixels tall.
        self.canvas = tk.Canvas(self.master, width=800, height=600, background="white")
        # This 'pack' method packs it into the top-level window.        
        self.canvas.pack()

        

        # Create a button with label "Draw", which calls the member
        # function self.draw() below when it's activated.
        self.draw_button = tk.Button(text="Draw", command=self.draw)
        # Pack the button into the window.
        self.draw_button.pack()

        # Create another button, with label "Clear" which calls the
        # member function self.clear() when it's activated.
        self.clear_button = tk.Button(text="Clear", command=self.clear)
        self.clear_button.pack()
        
    def draw(self):
        #############################################
        # Add your code to this method. Be sure to  #
        # store the 'handles' in the same way as    #
        # the sample code, so that the objects      #
        # will be removed when the 'clear button'   #
        # is clicked.                               #
        #############################################

        # The canvas methods .create_XXXXX actually return
        # an internal name (integer) corresponding to each
        # object we create, called a 'handle. 
        # We will store those handles so that when the 'clear button'
        # is clicked, we can ask the canvas to remove them.

        # Create/draw an oval on the canvas.
        # The first four numbers describe a bounding
        # box for the oval: (250, 150) is one corner,
        # and (550, 450) is another.
        h = self.canvas.create_oval(250,150,550,450,fill="blue")
        self.handle_list.append(h)

        # This would create/draw a rectangle on the canvas,
        # if we wanted one.
        # self.canvas.create_rectangle(250, 150, 550, 450)


        # Draw a bunch of lines.
        for n in range(0,300,20):
            h = self.canvas.create_line(400,n,400+n,300)
            self.handle_list.append(h)
            h = self.canvas.create_line(400+n,300,400,600-n)
            self.handle_list.append(h)
            h = self.canvas.create_line(400,600-n,400-n,300)
            self.handle_list.append(h)
            h = self.canvas.create_line(400-n,300,400,n)
            self.handle_list.append(h)
            
    ##############################################
    def clear(self):
        # To clear the things we drew in the 'draw'
        # function, we just ask the canvas to delete them,
        # one at a time, by their handles.
        while len(self.handle_list)>0:
            h = self.handle_list.pop()
            self.canvas.delete(h)


########################################
# Do not change anything below here!   #
########################################
# Instantiate the Tk class.
# This should only ever be done once in a program.
# Think of it as 'firing up' the library, getting it ready to do stuff.
root = tk.Tk()

# Create an instance of the MyApplication class we defined above.
app = MyApplication(master=root)

# Pass flow control over to the Tkinter library, so it can do things
# like wait for keyboard and mouse events, redraw the window when needed,...
# One of the things it will do is watch for buttons we created and invoke
# the 'callback functions' we gave them. It will run indefinitely,
# until the operating system sends it a 'quit' command (e.g.,
# if we close the window).
app.mainloop()

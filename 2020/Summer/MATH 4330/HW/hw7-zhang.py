# hw7.py
# Implementation of a simple Polynomial class

class Polynomial:
    def __init__(self):
        self.coefs = []

    def set_from_list(self, coefficient_list):
        # coefficient_list[k] will be the coefficient of x^k.
        self.coefs = coefficient_list[:]
        print(self.coefs)

    def degree(self):
        # Return the degree of this Polynomial object
        d = len(self.coefs)-1
        while d>=0 and self.coefs[d] == 0:
            d -= 1
        if d < 0:
            # This is the zero polynomial, which actually
            # has degree -infinity. But we will just return 0.
            return 0
        return d
    
    def get_string(self):
        # Return a string representation of this polynomial
        n = self.degree()
        if n < 0:
            return "0"
        # The polynomial starts with the constant term.
        # If there are more terms, we will concatenate them
        # on to the string 'result' below.
        result = str(self.coefs[0])
        if n==0:
            return result
        for i in range(1,n+1):
            # Figure out what string to concatenate on to 'result'
            # for this term.
            c = self.coefs[i]
            if (c < 0):
                result += " - {0}".format(-c)
            else:
                result += " + {0}".format(c)
            if i==1:
                result += "x"
            else:
                result += "x^{0}".format(i)
        return result

    def evaluate(self, t):
        result = 0
        n = len(self.coefs)
        # iterate every term
        for i in range(0,n):
            c = self.coefs[i]
            # sum up each term by using (coeffient times x to the i's power)
            result += (c * t**i)
        return result

    def derivative(self):
        t = Polynomial()
        coef_list = []
        n = len(self.coefs)
        for i in range(1,n):
            c = self.coefs[i]
            # based on power rule, the new coef is power times previous coef
            coef = c * i
            coef_list.insert(i-1,coef)
        t.set_from_list(coef_list)
        return t


############################################
# Do NOT change anything below here!!!!!!! #
############################################
f = Polynomial()
# This next line sets 'f' to be the polynomial
# f(x) = 1 -2x + x^4.
f.set_from_list([1,-2,0,0,1])
print("f(x) = "+f.get_string())

# Input from the user a number c at which
# to evaluate the polynomial.
c = float(input("Enter a number c: "))
y = f.evaluate(c)
print("f({0}) = {1}".format(c,y))

# Determine and print the derivative:
df = f.derivative()
print("f'(x) = "+df.get_string())
# Evaluate and print the derivative at c.
print("f'({0}) = {1}".format(c, df.evaluate(c)))

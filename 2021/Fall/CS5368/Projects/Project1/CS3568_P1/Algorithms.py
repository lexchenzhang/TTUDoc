import util


class DFS(object):
    def depthFirstSearch(self, problem):
        """
        Search the deepest nodes in the search tree first
        [2nd Edition: p 75, 3rd Edition: p 87]

        Your search algorithm needs to return a list of actions that reaches
        the goal.  Make sure to implement a graph search algorithm
        [2nd Edition: Fig. 3.18, 3rd Edition: Fig 3.7].

        To get started, you might want to try some of these simple commands to
        understand the search problem that is being passed in:

        print "Start:", problem.getStartState()
        print "Is the start a goal?", problem.isGoalState(problem.getStartState())
        print "Start's successors:", problem.getSuccessors(problem.getStartState())
        """
        "*** TTU CS3568 YOUR CODE HERE ***"
        path = []
        visited = []

        # stack is a suitable datastructure for DFS
        stack = util.Stack()
        start = (problem.getStartState(), [])
        # push stating point to stack
        stack.push(start)

        while not stack.isEmpty():
            # alwasys pop the top element
            (state, path) = stack.pop()
            if problem.isGoalState(state):
                path = path
                break

            if state not in visited:
                # keep tracking visited nodes
                visited.append(state)
                for currState, currPath, cost in problem.getSuccessors(state):
                    newPath = path + [currPath]
                    newState = (currState, newPath)
                    # add sub-nodes to stack
                    stack.push(newState)

        return path
        util.raiseNotDefined()


class BFS(object):
    def breadthFirstSearch(self, problem):
        "*** TTU CS3568 YOUR CODE HERE ***"
        path = []
        visited = []

        # queue is a suitable datastructure for DFS
        queue = util.Queue()
        start = (problem.getStartState(), [])
        # push stating point to queue
        queue.push(start)

        while not queue.isEmpty():
            # alwasys pop the top element
            (state, path) = queue.pop()
            if problem.isGoalState(state):
                path = path
                break

            if state not in visited:
                # keep tracking visited nodes
                visited.append(state)
                for currState, currPath, cost in problem.getSuccessors(state):
                    newPath = path + [currPath]
                    newState = (currState, newPath)
                    # add sub-nodes to queue
                    queue.push(newState)

        return path
        util.raiseNotDefined()


class UCS(object):
    def uniformCostSearch(self, problem):
        "*** TTU CS3568 YOUR CODE HERE ***"
        path = []
        visited = []

        # priority queue is a suitable datastructure for UCS
        heap = util.PriorityQueue()
        # we add a cost value here
        start = (problem.getStartState(), [], 0)
        # push stating point to heap
        heap.push(start, start[2])

        while not heap.isEmpty():
            # alwasys pop the top element
            (state, path, cost) = heap.pop()
            if problem.isGoalState(state):
                path = path
                break

            if state not in visited:
                # keep tracking visited nodes
                visited.append(state)
                for currState, currPath, currCost in problem.getSuccessors(state):
                    newPath = path + [currPath]
                    newCost = cost + currCost
                    newState = (currState, newPath, newCost)
                    # add sub-nodes to heap
                    heap.push(newState, newCost)
        return path
        util.raiseNotDefined()
class aSearch (object):
    def nullHeuristic(state, problem=None):
        """
        A heuristic function estimates the cost from the current state to the nearest goal in the provided SearchProblem.  This heuristic is trivial.
        """
        return 0

    def aStarSearch(self, problem, heuristic=nullHeuristic):
        "Search the node that has the lowest combined cost and heuristic first."
        "*** TTU CS3568 YOUR CODE HERE ***"

        # similar to UCS but with a heuristic weight
        path = []
        visited = []

        heap = util.PriorityQueue()
        start = (problem.getStartState(), [], 0)
        heap.push(start, 0)

        while not heap.isEmpty():

            state, path, cost = heap.pop()

            if state not in visited:
                visited.append(state)
                if problem.isGoalState(state):
                    path = path
                    break
                for currState, currPath, currCost in problem.getSuccessors(state):
                    newPath = path + [currPath]
                    newCost = cost + currCost
                    newState = (currState, newPath, newCost)
                    # here we need heuristic function
                    heap.push(newState, newCost + heuristic(currState, problem))
        return path


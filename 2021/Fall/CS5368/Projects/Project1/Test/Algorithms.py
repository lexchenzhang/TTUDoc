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

        # result用于记录访问的路径与顺序 visited用于记录是否被访问过
        result = []
        visited = []

        # 使用栈来模拟深度最深的节点先拓展的逻辑
        stack = util.Stack()
        start = (problem.getStartState(), [])
        # 将初始状态入栈
        stack.push(start)


        while not stack.isEmpty():
            # 出栈的是栈顶元素 就是最近入栈的元素 就是深度最深的节点
            (state, path) = stack.pop()
            if problem.isGoalState(state):
                result = path
                break

            if state not in visited:
                # 将该元素打上访问过了的标记
                visited.append(state)
                for currState, currPath, cost in problem.getSuccessors(state):
                    newPath = path + [currPath]
                    newState = (currState, newPath)
                    # 拓展节点状态全部入栈
                    stack.push(newState)
        # 因为是深搜 所以一条路走到底 能较快的找到一条路径
        return result

        util.raiseNotDefined()

class BFS(object):
    def breadthFirstSearch(self, problem):
        "*** TTU CS3568 YOUR CODE HERE ***"

        util.raiseNotDefined()

class UCS(object):
    def uniformCostSearch(self, problem):
        "*** TTU CS3568 YOUR CODE HERE ***"

        util.raiseNotDefined()

class aSearch (object):
    def nullHeuristic( state, problem=None):
        """
        A heuristic function estimates the cost from the current state to the nearest goal in the provided SearchProblem.  This heuristic is trivial.
        """
        return 0
    def aStarSearch(self,problem, heuristic=nullHeuristic):
        "Search the node that has the lowest combined cost and heuristic first."
        "*** TTU CS3568 YOUR CODE HERE ***"

        util.raiseNotDefined()


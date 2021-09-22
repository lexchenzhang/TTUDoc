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

        """
        # create fringe to store nodes
        fringe = util.Stack()
        # track visited nodes
        visited = []
        # push initial state to fringe
        fringe.push((problem.getStartState(), [], 1))

        while not fringe.isEmpty():
            node = fringe.pop()
            state = node[0]
            actions = node[1]
            # visited node
            # goal check
            if problem.isGoalState(state):
                return actions
            if state not in visited:
                visited.append(state)
                # visit child nodes
                successors = problem.getSuccessors(state)
                for child in successors:
                    # store state, action and cost = 1
                    child_state = child[0]
                    child_action = child[1]
                    if child_state not in visited:
                        # add child nodes
                        child_action = actions + [child_action]
                        fringe.push((child_state, child_action, 1))
        """

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

        # create fringe to store nodes
        fringe = util.Queue()
        # track visited nodes
        visited = []
        # push initial state to fringe
        fringe.push((problem.getStartState(), [], 1))

        while not fringe.isEmpty():
            node = fringe.pop()
            state = node[0]
            actions = node[1]
            # goal check
            if problem.isGoalState(state):
                return actions
            if state not in visited:
                visited.append(state)
                # visit child nodes
                successors = problem.getSuccessors(state)
                for child in successors:
                    # store state, action and cost = 1
                    child_state = child[0]
                    child_action = child[1]
                    if child_state not in visited:
                        # add child nodes
                        child_action = actions + [child_action]
                        fringe.push((child_state, child_action, 1))

        util.raiseNotDefined()


class UCS(object):
    def uniformCostSearch(self, problem):
        "*** TTU CS3568 YOUR CODE HERE ***"

        closed = []
        fringe = util.PriorityQueue()
        fringe.push((problem.getStartState(), 0, []), 0)

        while not fringe.isEmpty():
            node, cost, directions = fringe.pop()

            if problem.isGoalState(node):
                return directions

            if not (node in closed):
                closed.append(node)

                for node, direction, step_cost in problem.getSuccessors(node):
                    fringe.push((node, cost + step_cost, directions + [direction]), cost + step_cost)
        if fringe.isEmpty():
            return []


class aSearch (object):
    def nullHeuristic(state, problem=None):
        """
        A heuristic function estimates the cost from the current state to the nearest goal in the provided SearchProblem.  This heuristic is trivial.
        """
        return 0

    def aStarSearch(self, problem, heuristic=nullHeuristic):
        "Search the node that has the lowest combined cost and heuristic first."
        "*** TTU CS3568 YOUR CODE HERE ***"

        # create fringe to store nodes
        fringe = util.PriorityQueue()
        # track visited nodes
        visited = []
        # push initial state to fringe
        fringe.push((problem.getStartState(), [], 0), heuristic(problem.getStartState(), problem))
        while not fringe.isEmpty():
            node = fringe.pop()
            state = node[0]
            actions = node[1]
            # goal check
            if problem.isGoalState(state):
                return actions

            if state not in visited:
                visited.append(state)
                # visit child nodes
                successors = problem.getSuccessors(state)
                for child in successors:
                    # store state, action and cost = 1
                    child_state = child[0]
                    child_action = child[1]
                    if child_state not in visited:
                        # add child nodes
                        child_action = actions + [child_action]
                        cost = problem.getCostOfActions(child_action)
                        fringe.push((child_state, child_action, 0), cost + heuristic(child_state, problem))

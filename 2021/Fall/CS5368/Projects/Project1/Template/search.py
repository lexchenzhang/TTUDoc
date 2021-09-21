# -- coding: utf-8 --
# ---------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util

class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return  [s, s, w, s, w, w, s, w]

def depthFirstSearch(problem):
    """
    Search the deepest nodes in the search tree first.

    Your search algorithm needs to return a list of actions that reaches the
    goal. Make sure to implement a graph search algorithm.

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:

    print("Start:", problem.getStartState())
    print("Is the start a goal?", problem.isGoalState(problem.getStartState()))
    print("Start's successors:", problem.getSuccessors(problem.getStartState()))
    """
    "*** YOUR CODE HERE ***"

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

def breadthFirstSearch(problem):
    """Search the shallowest nodes in the search tree first."""
    "*** YOUR CODE HERE ***"

    # 宽搜和深搜的代码没有变化 只是换了数据结构 采用queue
    # 但是实现逻辑完全不一样 因为 出栈和出队的区别 出栈出来的是最深的节点 出队出的是最浅的节点
    # 则访问的顺序就不一样 是一层一层的往下搜索 在单位权值的情况下 能找到一条最短路

    result = []
    visited = []

    q = util.Queue()
    start = (problem.getStartState(), [])
    q.push(start)

    while not q.isEmpty():
        (state, path) = q.pop()
        if problem.isGoalState(state):
            result = path
            break

        if state not in visited:
            visited.append(state)
            for currState, currPath, cost in problem.getSuccessors(state):
                newPath = path + [currPath]
                newState = (currState, newPath)
                q.push(newState)

    return result
    util.raiseNotDefined()

def uniformCostSearch(problem):
    """Search the node of least total cost first."""
    "*** YOUR CODE HERE ***"

     # ucs和宽搜的代码没有大的变化 只是换了数据结构 采用PriorityQueue
    # 但是实现逻辑完全不一样 因为 采用的是堆 这里提供的的是小根堆 
    # 所以我们每次除了状态 还要放入一个优先级 每次弹出的都是优先级的值最小的状态 
    # 每次节点拓展的时候 getSuccessors函数都有给我们返回代价的  将节点的累计代价作为优先级 累计代价越小 优先级的值越小 越早出堆
    # ucs找最短路的时候是bfs的广义化 ucs允许权值可变 借助堆的特性 找到一条累计代价最小的路线
    # 因为每次都是出堆的时候都是当前代价最小的状态 那么达到最终状态的时候肯定就是全局的最小代价的路径

    result = []
    visited = []

    heap = util.PriorityQueue()
    start = (problem.getStartState(), [], 0)
    heap.push(start, start[2])

    while not heap.isEmpty():
        (state, path, cost) = heap.pop()
        if problem.isGoalState(state):
            result = path
            break

        if state not in visited:
            visited.append(state)
            for currState, currPath, currCost in problem.getSuccessors(state):
                newPath = path + [currPath]
                newCost = cost + currCost
                newState = (currState, newPath, newCost)
                heap.push(newState, newCost)

    return result
    util.raiseNotDefined()

def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0

def aStarSearch(problem, heuristic=nullHeuristic):
    """Search the node that has the lowest combined cost and heuristic first."""
    "*** YOUR CODE HERE ***"

    # astar 在代码的实现上其实和上面的ucs一样 但是他的放入堆的 优先级的值 除了累计代价还有一个启发函数的值
    # 启发函数返回这个状态距离最终状态的估算距离 因为ucs很好 但是很慢 加上启发不影响ucs的最优性 还能加速找到最短路
    # 这题其实我们只是简单的用它上面定义好的启发函数就好了 没有必要自己写
    result = []
    visited = []

    heap = util.PriorityQueue()
    start = (problem.getStartState(), [], 0)
    heap.push(start, 0)

    while not heap.isEmpty():

        state, path, cost = heap.pop()

        if state not in visited:
            visited.append(state)
            if problem.isGoalState(state):
                result = path
                break
            for currState, currPath, currCost in problem.getSuccessors(state):
                newPath = path + [currPath]
                newCost = cost + currCost
                newState = (currState, newPath, newCost)
                heap.push(newState, newCost + heuristic(currState, problem))
    return result
    util.raiseNotDefined()


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch


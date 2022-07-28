-- Welcome to my initial Lua test!
-- I love this language because it is simple, effective and fast

-- Import our library as myLib
myLib = require('mylib')

-- Print text to the terminal
print('Hi!')
print('This is being executed on the ScriptInit event!')

test = ""

-- This is a function
function myFunction()
    print('Super cool function')
end

-- Call a function
myFunction()

-- Call a custom function to execute some java code
myLib.customFunc("Lol")

-- This function will be executed when we call the TestEvent in the java code
function TestEvent()
    print('This is being executed on the TestEvent event!')
end

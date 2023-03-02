import keyboard

from core.clientServer.networkServer import NetworkServer

if __name__ == '__main__':
    server = NetworkServer()
    port = 10000
    server.init(port)
    server.start()
    print("Система запущена!")
    print("Для выхода введите q")
    keyboard.wait("q")
    server.stop()
    server.done()
    print("Работа завершена")

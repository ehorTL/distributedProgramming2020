Программа демонстрирует технологию RMI удаленного вызова методов.
Программа запускает сервер и клиент в параллельных потоках.
Клиент выполняет вызов методов объекта на сервере и получает результаты в виде
массивов запрашиваемых объектов.

Изза возможного обращения к серверу, а именно к объекту сервера нескольких клиентов одновременно, 
объект должен реализовывать методы потокобезопасно (искользовать синхронизаторы).
В данном случае объект не имеет полей данных, поэтому приложение является потокобезопасным.
За безопасность транзацкий отвечает сама база даннных. 
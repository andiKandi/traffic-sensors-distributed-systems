start:
	mvn clean package docker-compose:up
	
logs:
	docker-compose logs -f

stop:
	mvn docker-compose:down

databases:
	docker-compose -f docker-compose.yml exec maxscale maxctrl list servers

killdb1:
	docker container kill group_d_1_master_1

killdb2:
	docker container kill group_d_1_slave1_1

killdb3:
	docker container kill group_d_1_slave2_1

restart:
	sudo docker container start group_d_1_master_1 group_d_1_slave1_1 group_d_1_slave2_1
	sleep 5
	docker-compose -f docker-compose.yml exec maxscale maxctrl list servers
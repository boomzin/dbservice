CREATE TABLE tools (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    definition JSONB NOT NULL
);

insert into tools(definition) values
	('{"name": "getAvailableWines", "params": [{"name": "description", "type": "string", "required": false, "description": "Поиск по описанию"}, {"name": "price_le", "type": "int", "required": false, "description": "Максимальная цена"}, {"name": "price_ge", "type": "int", "required": false, "description": "Минимальная цена"}, {"name": "quantity_ge", "type": "int", "required": false, "description": "Минимальное количество на складе"}, {"name": "quantity_le", "type": "int", "required": false, "description": "Максимальное количество на складе"}], "endpoint": "/api/rest/db_service/v1/products", "httpMethod": "GET", "description": "Список вин в наличии с фильтрацией. Можно указать: description (поиск по описанию), price_le, price_ge, quantity_ge. Если параметр фильтра не нуже не используй его. Если спрашивают не алкоголь то переспроси"}'),
	('{"name": "addProduct", "params": [{"name": "description", "type": "string", "required": true, "description": "Описание вина"}, {"name": "price", "type": "int", "required": true, "description": "Цена"}, {"name": "quantity", "type": "int", "required": true, "description": "Количество бутылок"}], "endpoint": "/api/rest/db_service/v1/products", "httpMethod": "POST", "description": "Добавить продукт на склад"}')

;

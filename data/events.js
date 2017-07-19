db.events.insert(  
   {  
      "_id":"1",
      "name":"Soirée de fin de stage",
      "dateStart": new Date("2017-07-31T18:30:00Z"),
      "dateEnd": new Date("2017-07-31T23:59:00Z"),
      "placeId":"ChIJiy_8q1fqBUgRqdiLKz848yI",
      "description":"Venez feter la fin du stage des trois esclaves!",
      "image":"1",
      "canceled":false,
      "owner":{  
         "_id":"1",
      },
      "eventType":{  
         "_id":"2",
         "label":"AfterWork"
      },
	  "comments":[{
		  "id":"1",
		  "text":"Bisous",
		  "datePost": new Date("2017-06-19T12:00:00Z"),
		  "person": {
			"email" : "mjaulin@sii.fr"
		  }
	  }],
	  "participants":[{
		"_id":"3",
		"pseudo":"La machine"
	  },{
		"_id":"2",
		"pseudo":"Toto"
	  }]
   });
db.events.insert(  
   {  
      "_id":"2",
      "name":"Conférence sur l'avenir",
      "dateStart": new Date("2017-09-24T12:00:00Z"),
      "dateEnd": new Date("2017-09-24T14:00:00Z"),
      "placeId":"ChIJy6rbS_brBUgRXzWPvQ0FDXg",
      "description":"Venez assister à une conférence endiablée sur l'avenir de notre pays ainsi que les tenants et aboutissants de l'energie renouvelable et son rôle dans ce troisième millénaire",
      "image":"2",
      "canceled":false,
      "owner":{  
         "_id":"2",
      },
      "eventType":{  
         "_id":"3",
         "label":"Conférence"
      },
	  "comments":[{
		  "_id":"1",
		  "text":"C'est la balle",
		  "datePost": new Date("2017-05-24T12:00:00Z"),
		  "person": {
			"email" : "mjaulin@sii.fr"
		  },
		  "responses":[{
			"_id":"1",
			"text":"Yes",
			  "datePost": new Date("2017-05-24T13:00:00Z"),
			  "person": {
				"email" : "toto@sii.fr"
			  }
		  }]
	  }],
	  "participants":[{
		"_id":"1",
		"pseudo":"Zbra"
	  }]
   });
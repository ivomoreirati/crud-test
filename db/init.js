db = db.getSiblingDB("crud");
db.createUser(
     {
       user: "crud",
       pwd: "crud",

       roles: [{"role":"readWrite","db":"crud"}]
     }
  );

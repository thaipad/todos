
var mike = {
    age: 24,
    getAge: function () {
        this.age;
        // alert(this.toString());
    }
}

var anna = {
    age: 21
}

mike.getAge();

mike.getAge.call(anna);

var proto = {
    getItems: function () {
        // var this_ = this;
        return this.items.reduce(function(sum, item) {
            return sum + this.name + " has " + item + "\n";
        }.bind(this), "");
    }

};

var person = {
    name: "Sergey",
    items: ["phone", "keys", "ace-cream"],
    __proto__: proto
    // [[Prototype]]: <proto>
};

// person.__proto__ = proto;
alert(person.getItems());
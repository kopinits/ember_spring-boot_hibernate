App = Ember.Application.create({LOG_TRANSITIONS: true});

App.Router.map(function () {
    this.route("index", {path: "/"});

    this.resource("tasks", function () {
        console.log("Inside tasks....");
        this.route("new", {path: "/new"});
        this.route("edit", {path: "/:task_id"});
    });

});

App.ApplicationSerializer = DS.RESTSerializer.extend({
    primaryKey: 'id'
});

App.ApplicationAdapter = DS.RESTAdapter.extend({
    host: 'http://localhost:8080'
});


App.Store = DS.Store.extend({
    adapter: 'App.ApplicationAdapter'
});


App.Task = DS.Model.extend({
    name: DS.attr('string'),
    description: DS.attr('string'),
    location: DS.attr('string'),
    startDate: DS.attr('string'),
    endDate: DS.attr('string')
});

App.TasksIndexRoute = Ember.Route.extend({

    setupController: function (controller) {
        var tasks = Ember.A();
        Ember.$.getJSON('http://localhost:8080/task/list', function (Tasks) {
            Tasks.forEach(function (data) {
                tasks.pushObject(data);
            });
        });
        controller.set('content', tasks);
    },

    renderTemplate: function () {
        this.render('tasks.index', {into: 'application'});
    }

});

App.TasksEditRoute = Ember.Route.extend({

    setupController: function (controller, model) {
        this.controllerFor('tasks.edit').setProperties({isNew: false, content: model});
    },

    renderTemplate: function () {
        this.render('tasks.edit', {into: 'application'});
    }

});

App.TasksNewRoute = Ember.Route.extend({
    setupController: function (controller, model) {
        var newTask = this.store.createRecord('task', {});
        this.controllerFor('tasks.edit').setProperties({isNew: true, content: newTask});
    },
    renderTemplate: function () {
        this.render('tasks.edit', {into: 'application'});
    }

});

App.TasksRoute = Ember.Route.extend({
    actions: {
        updateItem: function (task) {
            $.ajax({
                type: "post",
                url: "/task/save/",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify(task),
                success: function (data) {
                    alert('Task updated');
                    window.location.replace("#/tasks");
                },
                error: function () {
                    alert('Error to save task');
                    window.location.replace("#/");
                }
            });
        },
        removeTask: function (id) {
            $.ajax({
                type: "delete",
                url: "/task/remove/",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    "id": id,
                    "name": null,
                    "description": null,
                    "startDate": null,
                    "endDate": null,
                    "location": null
                }), success: function () {
                    alert('Task deleted');
                    window.location.replace("#/");
                }, error: function () {
                    alert('Error to delete task');
                    window.location.replace("#/");
                }
            });
        }
    }
});

App.TasksEditController = Ember.ObjectController.extend({
    isNew: function () {
        console.log("calculating isNew");
        return this.get('content').get('id');
    }.property()
});


App.TasksIndexController = Ember.ArrayController.extend({
    tasksPresent: function () {
        var itemsPresent = this.get('content').get('length') > 0;
        return itemsPresent;
    }.property("content.@each")
});

App.NavView = Ember.View.extend({
    tagName: 'li',
    classNameBindings: ['active'],

    didInsertElement: function () {
        this._super();
        this.notifyPropertyChange('active');
        var _this = this;
        this.get('parentView').on('click', function () {
            _this.notifyPropertyChange('active');
        });
    },

    active: function () {
        return this.get('childViews.firstObject.active');
    }.property()
});

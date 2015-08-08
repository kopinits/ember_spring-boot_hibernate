App = Ember.Application.create();

App.Task = Ember.Object.extend({
  id: '',
  name: '',
  description: '',
  startDate: '',
  endDate: '',
  location: '',
  slug: function() {
    return this.get('name').dasherize();
  }.property('name'),
});

App.Task.reopenClass({
  createRecord: function(data) {
    var Task = App.Task.create({ id: data.id, name: data.name, description:data.description,
    startDate:data.startDate, endDate:data.endDate, location:data.location});
    return Task;
  }
});

App.Router.map(function() {
  this.route('tasks', { path: ':slug' });
});

App.IndexRoute = Ember.Route.extend({
  beforeModel: function() {
    this.transitionTo('tasks');
  }
});

App.TasksRoute = Ember.Route.extend({
  model: function() {
    var TaskObjects = Ember.A();
    Ember.$.getJSON('http://localhost:8080/task/list', function(Tasks) {
      Tasks.forEach(function(data) {
        TaskObjects.pushObject(App.Task.createRecord(data));
      });
    });
    return TaskObjects;
  },
  actions: {
    createTask: function() {
      var name = this.get('controller').get('newName');
      var description = this.get('controller').get('newDescription');
      var startDate = this.get('controller').get('newStartDate');
      var endDate = this.get('controller').get('newEndDate');
      var newlocation = this.get('controller').get('newLocation');

      Ember.$.ajax('http://localhost:8080/task/save', {
        type: 'POST',
        dataType: 'json',
        data: { id: null, name: name, description:description,
          startDate:startDate, endDate:endDate, location:newlocation },
        context: this,
        success: function(data) {
          var Task = App.Task.createRecord(data);
          this.modelFor('tasks').pushObject(Task);
          this.get('controller').set('newName', '');
          this.get('controller').set('newDescription', '');
          this.get('controller').set('newStartDate', '');
          this.get('controller').set('newEndDate', '');
          this.get('controller').set('newLocation', '');
        },
        error: function() {
          alert('Failed to save Task');
        }
      });
    },
    removeTask: function(id) {
      Ember.$.ajax('http://localhost:8080/task/remove', {
        type: 'DELETE',
        dataType: 'json',
        data: { id: id, name: null, description:null,
          startDate:null, endDate:null, location:null },
        context: this,
        success: function(data) {
          alert('Task removed');
        },
        error: function() {
          alert('Failed to save Task');
        }
      });
    }
  }
});


App.TasksController = Ember.ArrayController.extend({
  newName: '',
  newDescription: '',
  newLocation: '',
  newStartDate: '',
  newEndDate: '',
  disabled: function() {
    return Ember.isEmpty(this.get('newName'));
  }.property('newName')
});


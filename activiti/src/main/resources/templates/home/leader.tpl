layout 'layout.tpl', title:'申请者', username:map.get('user').getUsername(),
  content: contents {
    div(class:'container') {
      if (globalMessage) {
        div (class:'alert alert-success', globalMessage)
      }

      p(){ul(class:'list-group'){
            map.get('taskList').each { task ->
                li(class:'list-group-item', "id: $task.id, status: $task.status")
            }
      }
    }}

    script(src:'/js/home/leader.js'){}
  }
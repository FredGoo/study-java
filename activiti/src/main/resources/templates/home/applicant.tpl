layout 'layout.tpl', title:'申请者', username:map.get('user').getUsername(),
  content: contents {
    div(class:'container') {
      if (globalMessage) {
        div (class:'alert alert-success', globalMessage)
      }

      form(method:'post'){
        div(class:'form-group row'){
            label(class:'col-sm-2 col-form-label', '开始日期')
            div(class:'col-sm-10'){
                input(class:'form-control datepicker', name: 'start', autocomplete:'off')
            }
        }
        div(class:'form-group row'){
            label(class:'col-sm-2 col-form-label', '结束日期')
            div(class:'col-sm-10'){
                input(class:'form-control datepicker', name: 'end', autocomplete:'off')
            }
        }
        div(class:'form-group row'){
            label(class:'col-sm-2 col-form-label', '处理人')
            div(class:'col-sm-10'){
                select(class:'form-control', name: 'assign', autocomplete:'off'){
                    option(value:'leader1', '领导1')
                }
            }
        }
        button(type:'submit', class:'btn btn-primary', '申请')
      }

      p(){ul(class:'list-group'){
            map.get('processList').each { process ->
                li(class:'list-group-item', "id: $process.id, status: $process.status, startDate: $process.startDate <a href='/home/applicant/delete/process/$process.id'>删除</a>")
            }
      }
    }}
    script(src:'/js/home/applicant.js'){}
  }
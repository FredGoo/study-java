layout 'layout.tpl', title:'申请者',
  content: contents {
    div(class:'container') {
      if (globalMessage) {
        div (class:'alert alert-success', globalMessage)
      }
      h3('用户: ' + user.getUsername())
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
    }
    script(src:'/js/home/applicant.js'){}
  }
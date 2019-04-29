yieldUnescaped '<!DOCTYPE html>'
html(lang:'en') {
  head {
    title(title)
    link(rel:'stylesheet', href:'/css/bootstrap.min.css')
    link(rel:'stylesheet', href:'/css/bootstrap-datepicker.min.css')
    script(src:'/js/lib/jquery.min.js'){}
    script(src:'/js/lib/bootstrap.min.js'){}
    script(src:'/js/lib/bootstrap-datepicker.min.js'){}
    script(src:'/js/lib/bootstrap-datepicker.zh-CN.min.js'){}
  }
  body {
    div(class:'container') {
      div(class:'navbar') {
        div(class:'navbar-inner') {
          a(class:'brand',
              href:'http://docs.groovy-lang.org/docs/latest/html/documentation/markup-template-engine.html') {
              yield 'Groovy - Layout'
          }

          p{ul(class:'list-group') {
            li(class:'list-group-item') {
              a(href:'/applicant/index') {
                yield '申请者'
              }
            }
            li(class:'list-group-item') {
              a(href:'/leader/leader1') {
                yield '领导1'
              }
            }
            li(class:'list-group-item') {
              a(href:'/leader/leader2') {
                yield '领导2'
              }
            }
          }

          h3("用户: $username")
        }}
      }

      div { content() }
    }
  }
}

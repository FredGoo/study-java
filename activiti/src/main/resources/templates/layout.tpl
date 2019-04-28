yieldUnescaped '<!DOCTYPE html>'
html(lang:'en') {
  head {
    title(title)
    link(rel:'stylesheet', href:'/css/bootstrap.min.css')
    link(rel:'stylesheet', href:'/css/bootstrap-datepicker.min.css')
    script(src:'/js/jquery.min.js'){}
    script(src:'/js/bootstrap.min.js'){}
    script(src:'/js/bootstrap-datepicker.min.js'){}
    script(src:'/js/bootstrap-datepicker.zh-CN.min.js'){}
  }
  body {
    div(class:'container') {
      div(class:'navbar') {
        div(class:'navbar-inner') {
          a(class:'brand',
              href:'http://docs.groovy-lang.org/docs/latest/html/documentation/markup-template-engine.html') {
              yield 'Groovy - Layout'
          }
          ul(class:'nav') {
            li {
              a(href:'/home/applicant') {
                yield '申请者'
              }
            }
          }
        }
      }
      h1(user){}
      div { content() }
    }
  }
}

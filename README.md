#KaraNotes
##客户端：
###登录
post提交<br />
`http://viakiba.cn:8080/KaraNotes/user/register`
```json
{
  "user_email":"8945423@qq.com",
  "user_password":"951357wer"
}
```
响应
```json
{"user_id":"834280982847684608",
"user_headimg":null,
"user_email":"8945423@qq.com",
"user_name":"8945423@qq.com",
"user_password":"",
"user_github":null,
"user_sex":null,
"user_path":"8945423d13063b8",
"user_signature":null,
"user_extra":null}
```
###注册
post提交 <br />
` http://viakiba.cn:8080/KaraNotes/user/login ` 
```json
{
  "user_email":"8945423@qq.com",
  "user_password":"951357wer"
}
```
响应
```json
{
  "user_id":"834280982847684608",
  "user_headimg":null,
  "user_email":"8945423@qq.com",
  "user_name":"8945423@qq.com",
  "user_password":"",
  "user_github":null,
  "user_sex":null,
  "user_path":"8945423d13063b8",
  "user_signature":null,
  "user_extra":null}
```
###新增文章
post提交 <br />
` http://viakiba.cn:8080/KaraNotes/article/insert` 
```json
{
  "tag_content":["tagname1","tagname2","tagname3"],
  "token_id":"456468731321445",
  "article_content":"正文内容",
  " article_abstract ":{
             " article_title":"文章标题",
             "classify_id":"文章分类的ID",
             "article_show_img":"文章摘要图片",
             "abstract_content":"文章摘要内容"
   }
}
```
响应
```json
{
  "article_id":"834280982847684608"
}
```
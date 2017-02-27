#KaraNotes
##客户端：
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
###删除文章
post提交 <br />
` http://viakiba.cn:8080/KaraNotes/article/delete` 
```json
{
  "token_id":"834280982847684608",
  "article_id":"834280982847684608"
}
```
响应<br/>
正确
```json
{
  "error":"flase"
}
```
错误
```json
{
  "error":"true"
}
```
###更新文章
post提交 <br />
` http://viakiba.cn:8080/KaraNotes/article/update` 
```json
{
  "tag_content":["tagname1","tagname2","tagname3"],
  "token_id":"456468731321445",
  "article_content":"正文内容",
  " article_abstract ":{
			 "article_id":"文章ID",
             " article_title":"文章标题",
             "classify_id":"文章分类的ID",
             "article_show_img":"文章摘要图片",
             "abstract_content":"文章摘要内容"
   }
}
```
响应<br/>
正确
```json
{
  "article_id":"834280982847684608"
}
```
错误
```json
{
  "error":"添加失败"
}
```
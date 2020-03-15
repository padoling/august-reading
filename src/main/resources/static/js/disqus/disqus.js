/**
*  RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
*  LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables*/

/*
실서버 배포 후 절대경로 등록해야 함
var disqus_config = function () {
this.page.url = '/posts/' + $('#postId').val();  // Replace PAGE_URL with your page's canonical URL variable
this.page.identifier = $('#postId').val(); // Replace PAGE_IDENTIFIER with your page's unique identifier variable
};
*/

(function() { // DON'T EDIT BELOW THIS LINE
var d = document, s = d.createElement('script');
s.src = 'https://august-reading.disqus.com/embed.js';
s.setAttribute('data-timestamp', +new Date());
(d.head || d.body).appendChild(s);
})();

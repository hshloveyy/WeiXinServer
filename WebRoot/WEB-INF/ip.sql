select t.* from ipdb t where t.ip_start like '1.2.4%';
			
            /*					  0.0.8.52								*/
			/*			  		  0.0.3.52
			/*	0.0.7.255		  1.28.80.0		1.28.87.255	内蒙古乌海市	联通*/
			/*			  		  1.28.83.52									*/
            /*					  1.28.88.52									*/
select 
substring_index(t.ip_start, '.', 1) a,
substring_index(substring_index(t.ip_start, '.', -3), '.', 1) b,
substring_index(substring_index(t.ip_start, '.', -2), '.', 1) c,
substring_index(t.ip_start, '.', -1) d
from ipdb t where t.ip_start = '1.28.80.0';


select t.* from ipdb t
where substring_index(t.ip_start, '.', 1) = substring_index('106.120.100.162', '.', 1)
and substring_index(substring_index(t.ip_start, '.', -3), '.', 1) = substring_index(substring_index('106.120.100.162', '.', -3), '.', 1)
and substring_index(substring_index(t.ip_start, '.', -2), '.', 1) <= substring_index(substring_index('106.120.100.162', '.', -2), '.', 1)
and substring_index(substring_index(t.ip_end, '.', -2), '.', 1) >= substring_index(substring_index('106.120.100.162', '.', -2), '.', 1)
and substring_index(t.ip_start, '.', -1) <= substring_index('222.247.33.126', '.', -1)
and substring_index(t.ip_end, '.', -1) > substring_index('222.247.33.126', '.', -1);
    
    
select t.*,
substring_index(t.ip_start, '.', -1) ,substring_index('1.28.88.52', '.', -1)
	, substring_index(t.ip_end, '.', -1) , substring_index('1.28.88.52', '.', -1)
    from ipdb t where t.ip_start = '1.28.88.0';

select * from ipdb t
where substring_index(t.ip_start, '.', -1) <= substring_index('1.28.88.52', '.', -1)
	and substring_index(t.ip_end, '.', -1) >= substring_index('1.28.88.52', '.', -1);
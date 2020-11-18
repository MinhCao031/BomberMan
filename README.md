# BomberMan To-do list (Có ý kiến gì thì comment vào đây nha)

1. Thiết kế cây thừa kế cho các đối tượng game (Xong, các class kế thừa Entity đã có command)

2. Xây dựng bản đồ màn chơi từ tệp cấu hình (Minh)

3. Di chuyển Bomber theo sự điều khiển từ người chơi (Minh)
- Bomb: Bom nổ sẽ phá hủy block, người chơi và địch trong phạm vi nổ của nó, xử lí va chạm của flame với các đối tượng khác
- Xử lý khi Bomber sử dụng các Item và khi đi vào vị trí Portal

4. Tự động di chuyển các Enemy (Đạt)
- Balloom: Di chuyển ngẫu nhiên (Hạn t3 tuàn sau)
- Oneal: Di chuyển nhanh hơn (Hạn t3 tuàn sau)
- Oneal: đuổi theo bomber nếu ở gần (Hạn cn tuàn sau)

5. Điểm số, Thời gian (Hằng, hạn t5 tuần sau)
- Điểm số bao gồm: lấy được item, diệt quái
- Thời gian: mỗi ván giới hạn ~200 giây, quá thời gian thì thua

6. Menu & âm thanh (Hoa, hạn t5 tuần sau)
- Menu gồm ít nhất: Nút start và nút Quit
- Ấn nút Start sẽ vào màn chơi + bắt đầu tính giờ + lồng âm thanh.
- Âm thanh có BackGroundMusic, Winning Music, Losing Music
- SoundEffect có bước chạy của bomber, tiếng đặt bomb, tiếng bomb nổ, tiếng Enemy chết, tiếng Bomber chết, thông báo khi không còn Enemy,...
- Khi ván chơi kết thúc sẽ về menu (làm 1 màn chơi nên tạm thời như vậy)

from pathlib import Path
from PIL import Image, ImageDraw, ImageFont
import math


ROOT = Path(__file__).resolve().parent
OUT_DIR = ROOT / "rendered_diagrams"
OUT_DIR.mkdir(exist_ok=True)


def load_font(size: int, bold: bool = False):
    candidates = [
        "C:/Windows/Fonts/malgunbd.ttf" if bold else "C:/Windows/Fonts/malgun.ttf",
        "C:/Windows/Fonts/NanumGothic.ttf",
    ]
    for path in candidates:
        if Path(path).exists():
            return ImageFont.truetype(path, size)
    return ImageFont.load_default()


TITLE_FONT = load_font(44, bold=True)
BOX_FONT = load_font(26)
SMALL_FONT = load_font(22)


BG = "#F8F6FB"
BOX_FILL = "#FFFFFF"
BOX_OUTLINE = "#6A56C9"
DECISION_FILL = "#FFF2C7"
DECISION_OUTLINE = "#B68A1F"
ARROW = "#3A3552"
ACCENT_BLUE = "#4E79A7"
ACCENT_GREEN = "#59A14F"
ACCENT_ORANGE = "#F28E2B"
ACCENT_PURPLE = "#8B6CDB"


def draw_multiline_center(draw, bbox, text, font, fill):
    x1, y1, x2, y2 = bbox
    lines = text.split("\n")
    spacing = 6
    line_boxes = [draw.textbbox((0, 0), line, font=font) for line in lines]
    widths = [b[2] - b[0] for b in line_boxes]
    heights = [b[3] - b[1] for b in line_boxes]
    total_h = sum(heights) + spacing * (len(lines) - 1)
    y = y1 + ((y2 - y1) - total_h) / 2
    for line, w, h in zip(lines, widths, heights):
        x = x1 + ((x2 - x1) - w) / 2
        draw.text((x, y), line, font=font, fill=fill)
        y += h + spacing


def draw_box(draw, x, y, w, h, text, fill=BOX_FILL, outline=BOX_OUTLINE):
    draw.rounded_rectangle((x, y, x + w, y + h), radius=24, fill=fill, outline=outline, width=4)
    draw_multiline_center(draw, (x + 16, y + 12, x + w - 16, y + h - 12), text, BOX_FONT, "#1F1B2D")
    return (x, y, x + w, y + h)


def draw_decision(draw, cx, cy, w, h, text):
    points = [(cx, cy - h / 2), (cx + w / 2, cy), (cx, cy + h / 2), (cx - w / 2, cy)]
    draw.polygon(points, fill=DECISION_FILL, outline=DECISION_OUTLINE)
    draw.line(points + [points[0]], fill=DECISION_OUTLINE, width=4)
    draw_multiline_center(draw, (cx - w / 2 + 20, cy - h / 2 + 10, cx + w / 2 - 20, cy + h / 2 - 10), text, SMALL_FONT, "#3F3310")
    return points


def draw_arrow(draw, start, end, label=None, label_offset=(0, 0)):
    draw.line((start, end), fill=ARROW, width=4)
    angle = math.atan2(end[1] - start[1], end[0] - start[0])
    arrow_len = 16
    wing = math.pi / 7
    p1 = (
        end[0] - arrow_len * math.cos(angle - wing),
        end[1] - arrow_len * math.sin(angle - wing),
    )
    p2 = (
        end[0] - arrow_len * math.cos(angle + wing),
        end[1] - arrow_len * math.sin(angle + wing),
    )
    draw.polygon([end, p1, p2], fill=ARROW)
    if label:
        lx = (start[0] + end[0]) / 2 + label_offset[0]
        ly = (start[1] + end[1]) / 2 + label_offset[1]
        box = draw.textbbox((0, 0), label, font=SMALL_FONT)
        draw.rounded_rectangle((lx - 24, ly - 18, lx + (box[2] - box[0]) + 24, ly + (box[3] - box[1]) + 18), radius=16, fill="#FFFFFF", outline=None)
        draw.text((lx, ly), label, font=SMALL_FONT, fill="#1F1B2D")


def draw_poly_arrow(draw, points, label=None, label_segment=0, label_offset=(0, 0)):
    for idx in range(len(points) - 1):
        draw.line((points[idx], points[idx + 1]), fill=ARROW, width=4)

    start = points[-2]
    end = points[-1]
    angle = math.atan2(end[1] - start[1], end[0] - start[0])
    arrow_len = 16
    wing = math.pi / 7
    p1 = (
        end[0] - arrow_len * math.cos(angle - wing),
        end[1] - arrow_len * math.sin(angle - wing),
    )
    p2 = (
        end[0] - arrow_len * math.cos(angle + wing),
        end[1] - arrow_len * math.sin(angle + wing),
    )
    draw.polygon([end, p1, p2], fill=ARROW)

    if label and 0 <= label_segment < len(points) - 1:
        a = points[label_segment]
        b = points[label_segment + 1]
        lx = (a[0] + b[0]) / 2 + label_offset[0]
        ly = (a[1] + b[1]) / 2 + label_offset[1]
        box = draw.textbbox((0, 0), label, font=SMALL_FONT)
        draw.rounded_rectangle(
            (lx - 24, ly - 18, lx + (box[2] - box[0]) + 24, ly + (box[3] - box[1]) + 18),
            radius=16,
            fill="#FFFFFF",
            outline=None,
        )
        draw.text((lx, ly), label, font=SMALL_FONT, fill="#1F1B2D")


def flowchart():
    img = Image.new("RGB", (2000, 3400), BG)
    draw = ImageDraw.Draw(img)
    draw.text((70, 40), "Ch10 Notification 전체 흐름도", font=TITLE_FONT, fill="#201B2B")

    bw, bh = 420, 92
    x = 790
    y = 130
    gap = 110

    a = draw_box(draw, x, y, bw, bh, "앱 실행", fill="#EAF1FF", outline=ACCENT_BLUE)
    b = draw_box(draw, x, y + gap, bw, bh, "MainActivity 화면 표시")
    c = draw_box(draw, x, y + gap * 2, bw, bh, "사용자가 '알림 발생'\n버튼 클릭")
    d_cx, d_cy = 1000, y + gap * 3 + 46
    d = draw_decision(draw, d_cx, d_cy, 430, 150, "Android 13\n이상인가?")

    e_cx, e_cy = 520, y + gap * 4 + 50
    e = draw_decision(draw, e_cx, e_cy, 430, 150, "POST_NOTIFICATIONS\n권한 있음?")

    f = draw_box(draw, 260, y + gap * 5 + 40, 320, bh, "권한 요청", fill="#FFF4E8", outline=ACCENT_ORANGE)
    g = draw_box(draw, 260, y + gap * 6 + 30, 320, bh, "권한 허용", fill="#EEF8EA", outline=ACCENT_GREEN)
    h = draw_box(draw, x, y + gap * 5 + 20, bw, bh, "showNotification() 호출", fill="#F0EBFF", outline=ACCENT_PURPLE)

    i_cx, i_cy = 1000, y + gap * 6 + 210
    i = draw_decision(draw, i_cx, i_cy, 430, 150, "Android 8\n이상인가?")
    j = draw_box(draw, 1290, y + gap * 7 + 170, 430, bh, "NotificationChannel\n생성 및 등록", fill="#EEF8EA", outline=ACCENT_GREEN)
    k = draw_box(draw, 320, y + gap * 7 + 170, 360, bh, "채널 없이 진행", fill="#FFF4E8", outline=ACCENT_ORANGE)

    l = draw_box(draw, x, y + gap * 8 + 310, bw, bh, "NotificationCompat.Builder 설정")
    m = draw_box(draw, x, y + gap * 9 + 310, bw, bh, "알림 제목 · 내용 · 아이콘 설정")
    n = draw_box(draw, x, y + gap * 10 + 310, bw, bh, "RemoteInput 생성")
    o = draw_box(draw, x, y + gap * 11 + 310, bw, bh, "PendingIntent.getBroadcast() 생성")
    p = draw_box(draw, x, y + gap * 12 + 310, bw, bh, "알림의 '답장' 액션 추가")
    q = draw_box(draw, x, y + gap * 13 + 310, bw, bh, "알림 표시", fill="#EAF1FF", outline=ACCENT_BLUE)
    r = draw_box(draw, x, y + gap * 14 + 310, bw, bh, "사용자가 알림창에서\n답장 입력 후 전송")
    s = draw_box(draw, x, y + gap * 15 + 310, bw, bh, "PendingIntent 실행")
    t = draw_box(draw, x, y + gap * 16 + 310, bw, bh, "Broadcast 전달")
    u = draw_box(draw, x, y + gap * 17 + 310, bw, bh, "ReplyReceiver.onReceive() 호출", fill="#F0EBFF", outline=ACCENT_PURPLE)
    v = draw_box(draw, x, y + gap * 18 + 310, bw, bh, "RemoteInput 입력값 추출")
    w_cx, w_cy = 1000, y + gap * 19 + 405
    w = draw_decision(draw, w_cx, w_cy, 430, 150, "입력값이\n비어 있는가?")
    x_end = draw_box(draw, 320, y + gap * 20 + 480, 300, bh, "처리 종료", fill="#FFF4E8", outline=ACCENT_ORANGE)
    y_box = draw_box(draw, 1260, y + gap * 20 + 480, 320, bh, "알림 갱신", fill="#EEF8EA", outline=ACCENT_GREEN)
    z = draw_box(draw, 1260, y + gap * 21 + 560, 320, bh, "Toast 메시지 출력", fill="#EEF8EA", outline=ACCENT_GREEN)

    center = lambda b: ((b[0] + b[2]) / 2, (b[1] + b[3]) / 2)
    bottom = lambda b: ((b[0] + b[2]) / 2, b[3])
    top = lambda b: ((b[0] + b[2]) / 2, b[1])

    for src, dst in [(a, b), (b, c)]:
        draw_arrow(draw, bottom(src), top(dst))
    draw_arrow(draw, bottom(c), (d_cx, d_cy - 75))
    draw_arrow(draw, (d_cx - 215, d_cy), (e_cx + 215, e_cy), label="예", label_offset=(-20, -30))
    draw_arrow(draw, (d_cx, d_cy + 75), top(h), label="아니오", label_offset=(40, 0))
    draw_arrow(draw, (e_cx, e_cy + 75), top(f), label="아니오", label_offset=(-60, 0))
    draw_arrow(draw, bottom(f), top(g))
    draw_arrow(draw, (e_cx + 215, e_cy), (x, h[1] + bh / 2), label="예", label_offset=(10, -30))
    draw_arrow(draw, (g[2], center(g)[1]), (x, center(h)[1]))
    draw_arrow(draw, bottom(h), (i_cx, i_cy - 75))
    draw_arrow(draw, (i_cx + 215, i_cy), (j[0], center(j)[1]), label="예", label_offset=(10, -30))
    draw_arrow(draw, (i_cx - 215, i_cy), (k[2], center(k)[1]), label="아니오", label_offset=(-40, -30))
    draw_arrow(draw, bottom(j), (1000, l[1]))
    draw_arrow(draw, bottom(k), (1000, l[1]))
    for src, dst in [(l, m), (m, n), (n, o), (o, p), (p, q), (q, r), (r, s), (s, t), (t, u), (u, v)]:
        draw_arrow(draw, bottom(src), top(dst))
    draw_arrow(draw, bottom(v), (w_cx, w_cy - 75))
    draw_arrow(draw, (w_cx - 215, w_cy), (x_end[2], center(x_end)[1]), label="예", label_offset=(-30, -30))
    draw_arrow(draw, (w_cx + 215, w_cy), (y_box[0], center(y_box)[1]), label="아니오", label_offset=(10, -30))
    draw_arrow(draw, bottom(y_box), top(z))

    return img


def architecture():
    img = Image.new("RGB", (2500, 1500), BG)
    draw = ImageDraw.Draw(img)
    draw.text((70, 40), "Ch10 Notification SW 설계구조", font=TITLE_FONT, fill="#201B2B")

    def box(x, y, w, h, text, fill=BOX_FILL, outline=BOX_OUTLINE):
        return draw_box(draw, x, y, w, h, text, fill=fill, outline=outline)

    user = box(110, 650, 240, 100, "사용자", fill="#EAF1FF", outline=ACCENT_BLUE)
    activity = box(430, 620, 360, 140, "MainActivity\nUI + 알림 생성", fill="#F0EBFF", outline=ACCENT_PURPLE)
    permission = box(980, 170, 340, 120, "권한 처리\nAndroid 13+", fill="#FFF4E8", outline=ACCENT_ORANGE)
    channel = box(980, 360, 340, 120, "채널 생성\nAndroid 8+", fill="#EEF8EA", outline=ACCENT_GREEN)
    builder = box(980, 560, 340, 120, "NotificationCompat\nBuilder", fill="#FFFFFF", outline=BOX_OUTLINE)
    remote = box(980, 760, 340, 120, "RemoteInput\n답장 입력", fill="#FFFFFF", outline=BOX_OUTLINE)
    manager = box(1520, 250, 380, 130, "NotificationManager\n알림 표시 / 갱신", fill="#EAF1FF", outline=ACCENT_BLUE)
    pending = box(1520, 740, 380, 130, "PendingIntent\ngetBroadcast()", fill="#FFFFFF", outline=BOX_OUTLINE)
    broadcast = box(2040, 740, 290, 120, "Broadcast", fill="#FFFFFF", outline=BOX_OUTLINE)
    receiver = box(1980, 1010, 400, 140, "ReplyReceiver\n백그라운드 입력 처리", fill="#F0EBFF", outline=ACCENT_PURPLE)
    constants = box(1180, 1180, 420, 120, "NotificationConstants\n공용 상수 관리", fill="#FFF4E8", outline=ACCENT_ORANGE)
    toast = box(2060, 1230, 240, 100, "Toast", fill="#EEF8EA", outline=ACCENT_GREEN)

    center = lambda b: ((b[0] + b[2]) / 2, (b[1] + b[3]) / 2)
    left = lambda b: (b[0], (b[1] + b[3]) / 2)
    right = lambda b: (b[2], (b[1] + b[3]) / 2)
    top = lambda b: ((b[0] + b[2]) / 2, b[1])
    bottom = lambda b: ((b[0] + b[2]) / 2, b[3])

    draw_arrow(draw, right(user), left(activity))
    draw_poly_arrow(
        draw,
        [right(activity), (890, right(activity)[1]), (890, center(permission)[1]), left(permission)],
        label="권한 확인",
        label_segment=1,
        label_offset=(-40, -40),
    )
    draw_poly_arrow(
        draw,
        [right(activity), (860, right(activity)[1]), (860, center(channel)[1]), left(channel)],
        label="채널 등록",
        label_segment=1,
        label_offset=(-30, -36),
    )
    draw_poly_arrow(
        draw,
        [right(activity), (900, center(builder)[1]), left(builder)],
        label="알림 구성",
        label_segment=1,
        label_offset=(-10, -36),
    )
    draw_poly_arrow(
        draw,
        [right(activity), (900, center(remote)[1]), left(remote)],
        label="입력 기능",
        label_segment=1,
        label_offset=(-10, 16),
    )
    draw_poly_arrow(
        draw,
        [right(builder), (1430, center(builder)[1]), (1430, center(manager)[1]), left(manager)],
        label="알림 표시",
        label_segment=1,
        label_offset=(20, -40),
    )
    draw_poly_arrow(
        draw,
        [right(remote), (1430, center(remote)[1]), (1430, center(pending)[1]), left(pending)],
        label="답장 액션 연결",
        label_segment=1,
        label_offset=(0, 18),
    )
    draw_poly_arrow(
        draw,
        [right(pending), left(broadcast)],
        label="Broadcast 전달",
        label_segment=0,
        label_offset=(0, -42),
    )
    draw_poly_arrow(
        draw,
        [bottom(broadcast), (center(broadcast)[0], 950), top(receiver)],
        label="이벤트 수신",
        label_segment=1,
        label_offset=(20, -36),
    )
    draw_poly_arrow(
        draw,
        [left(receiver), (1880, center(receiver)[1]), (1880, center(manager)[1]), right(manager)],
        label="알림 갱신",
        label_segment=1,
        label_offset=(20, -40),
    )
    draw_poly_arrow(
        draw,
        [bottom(receiver), top(toast)],
        label="처리 결과",
        label_segment=0,
        label_offset=(18, -36),
    )

    draw_poly_arrow(
        draw,
        [top(constants), (top(constants)[0], 990), (center(activity)[0], 990), bottom(activity)],
    )
    draw_poly_arrow(
        draw,
        [right(constants), (1840, center(constants)[1]), (1840, bottom(receiver)[1] - 20), bottom(receiver)],
    )
    draw.text((1290, 1320), "공용 키 / 채널 ID 공유", font=SMALL_FONT, fill="#5A4621")

    return img


def main():
    flow = flowchart()
    arch = architecture()
    flow_path = OUT_DIR / "flowchart_diagram.png"
    arch_path = OUT_DIR / "sw_architecture_diagram.png"
    pdf_path = OUT_DIR / "ch10_diagrams.pdf"
    flow.save(flow_path)
    arch.save(arch_path)
    flow.convert("RGB").save(pdf_path, save_all=True, append_images=[arch.convert("RGB")])
    print(flow_path)
    print(arch_path)
    print(pdf_path)


if __name__ == "__main__":
    main()

import csv
import math
from pathlib import Path

root = Path(__file__).resolve().parent.parent

with open(root / "results/results.csv", newline="") as file:
    rows = list(csv.DictReader(file))


def slope(values, counts):
    x = [math.log(v) for v in values]
    y = [math.log(v) for v in counts]
    ax = sum(x) / len(x)
    ay = sum(y) / len(y)

    top = sum((a - ax) * (b - ay) for a, b in zip(x, y))
    bottom = sum((a - ax) ** 2 for a in x)

    return top / bottom


lines = [
    "## 4. Measurements",
    "",
    "The table below shows measured work divided by the expected complexity formula.",
    "",
    "| Algorithm | Density | Ratio range |",
    "|---|---|---|"
]

names = ["Kruskal", "Prim", "Kosaraju", "Kahn", "DagShortestPath"]

for name in names:
    for density in ["sparse", "dense"]:
        data = [
            r for r in rows
            if r["algorithm"] == name
            and r["graph"].endswith(density)
        ]

        data.sort(key=lambda r: int(r["n"]))

        if len(data) != 5:
            raise ValueError("Missing benchmark results")

        ratios = []

        for r in data:
            n = int(r["n"])
            m = int(r["m"])
            operations = (
                int(r["dsu_ops"])
                + int(r["edge_scans"])
                + int(r["comparisons"])
            )

            if name == "Kruskal":
                formula = m * math.log2(m)
            elif name == "Prim":
                formula = (n + m) * math.log2(n + 1)
            else:
                formula = n + m

            ratios.append(operations / formula)

        lines.append(
            f"| {name} | {density} | "
            f"{min(ratios):.3f} - {max(ratios):.3f} |"
        )

lines.extend(["", "### Log-Log Slopes", ""])

for name in ["Kosaraju", "Kahn", "DagShortestPath"]:
    for density in ["sparse", "dense"]:
        data = [
            r for r in rows
            if r["algorithm"] == name
            and r["graph"].endswith(density)
        ]
        data.sort(key=lambda r: int(r["n"]))

        sizes = [int(r["n"]) for r in data]
        scans = [int(r["edge_scans"]) for r in data]

        lines.append(
            f"- {name} ({density}), edge scans: "
            f"{slope(sizes, scans):.3f}"
        )

for density in ["sparse", "dense"]:
    data = [
        r for r in rows
        if r["algorithm"] == "Kruskal"
        and r["graph"].endswith(density)
    ]
    data.sort(key=lambda r: int(r["n"]))

    sizes = [int(r["n"]) for r in data]
    comparisons = [int(r["comparisons"]) for r in data]

    lines.append(
        f"- Kruskal ({density}), comparisons: "
        f"{slope(sizes, comparisons):.3f}"
    )

lines.extend([
    "",
    "The traversal slopes can be compared with the expected linear slope of 1. "
    "Kruskal also requires sorting, so its comparison count includes a logarithmic factor.",
    ""
])

report_path = root / "REPORT.md"
report = report_path.read_text(encoding="utf-8")

before, separator, remaining = report.partition("## 4. Measurements")

if not separator:
    raise ValueError("Measurements section not found")

after_pos = remaining.find("## 5. Algorithm Choice")

if after_pos == -1:
    raise ValueError("Algorithm choice section not found")

after = remaining[after_pos:]

report_path.write_text(
    before + "\n".join(lines) + "\n" + after,
    encoding="utf-8"
)

print("Report measurements updated")

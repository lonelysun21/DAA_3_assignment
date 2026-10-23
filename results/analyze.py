import csv
import math
from pathlib import Path

folder = Path(__file__).resolve().parent

with open(folder / "results.csv", newline="") as file:
    rows = list(csv.DictReader(file))


def slope(data, column):
    x = [math.log(int(row["n"])) for row in data]
    y = [math.log(int(row[column])) for row in data]

    avg_x = sum(x) / len(x)
    avg_y = sum(y) / len(y)

    top = sum(
        (a - avg_x) * (b - avg_y)
        for a, b in zip(x, y)
    )

    bottom = sum((a - avg_x) ** 2 for a in x)

    return top / bottom


lines = [
    "Algorithm Complexity Analysis",
    ""
]

algorithms = [
    "Kruskal",
    "Prim",
    "Kosaraju",
    "Kahn",
    "DagShortestPath"
]

for algorithm in algorithms:
    for density in ["sparse", "dense"]:
        data = [
            row for row in rows
            if row["algorithm"] == algorithm
            and row["graph"].endswith(density)
        ]

        data.sort(key=lambda row: int(row["n"]))

        scan_slope = slope(data, "edge_scans")

        ratios = [
            int(row["edge_scans"]) / int(row["m"])
            for row in data
        ]

        lines.append(f"{algorithm} ({density})")
        lines.append(f"Edge scans slope: {scan_slope:.3f}")
        lines.append(f"Scans per edge: {min(ratios):.3f} - {max(ratios):.3f}")

        if algorithm == "Kruskal":
            dsu_ratios = [
                int(row["dsu_ops"]) / int(row["m"])
                for row in data
            ]

            lines.append(
                f"DSU ops per edge: {min(dsu_ratios):.3f} - {max(dsu_ratios):.3f}"
            )

        if algorithm in ["Kruskal", "Prim"]:
            comparison_slope = slope(data, "comparisons")
            lines.append(f"Comparison slope: {comparison_slope:.3f}")

        lines.append("")

output = "\n".join(lines)

(folder / "analysis.txt").write_text(output, encoding="utf-8")
print(output)
